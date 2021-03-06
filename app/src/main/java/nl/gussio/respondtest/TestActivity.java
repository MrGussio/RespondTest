package nl.gussio.respondtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class TestActivity extends AppCompatActivity {

    private int count, minTimer, maxTimer;
    private boolean sight, sound;
    private long cooldown=0L, resetCooldown=0L, timer=0L;
    private RelativeLayout layout;
    private long[] results;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        mediaPlayer = MediaPlayer.create(this, R.raw.beep);

        layout = findViewById(R.id.sightLayout);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        count = sharedPref.getInt("testCount", R.integer.testCountDefault);
        minTimer = sharedPref.getInt("minCount", R.integer.minCountDefault)*1000;
        maxTimer = sharedPref.getInt("maxCount", R.integer.maxCountDefault)*1000;
        sight = sharedPref.getBoolean("sight", false);
        sound = sharedPref.getBoolean("sound", false);
        cooldown = generateRandomTimeValue();
        results = new long[count];
        layout.performClick();
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(timer > 0L){
                    long current = System.currentTimeMillis();
                    long delta = current-timer;
                    results[results.length-count]=delta;
                    count--;
                    timer = 0L;
                    cooldown = 0L;
                    resetCooldown = System.currentTimeMillis()+500;
                    layout.setBackgroundColor(Color.GREEN);
                    if(sound && mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }else if(cooldown > 0L){
                    results[results.length-count]=-1;
                    count--;
                    timer = 0L;
                    cooldown = 0L;
                    resetCooldown = System.currentTimeMillis()+500;
                    layout.setBackgroundColor(Color.RED);
                    if(sound && mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }
                return false;
            }
        });
        update();
    }

    private void update() {
        new Thread() {
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resetCooldown == 0L && cooldown != 0 && cooldown < System.currentTimeMillis()) { //Wanneer de cooldown voorbij is
                                cooldown = 0L;
                                if(sight)
                                    layout.setBackgroundColor(Color.BLUE);
                                if(sound){
                                    mediaPlayer.setLooping(true);
                                    mediaPlayer.start();
                                }
                                timer = System.currentTimeMillis();
                            }
                            if(resetCooldown > 0L && resetCooldown < System.currentTimeMillis()){//Wanneer de resetcooldown voorbij is
                                resetCooldown = 0L;
                                layout.setBackgroundColor(Color.GRAY);
                                if(count == 0){
                                    String message = "";
                                    int average = 0;
                                    int count = 0;
                                    for(int i = 0; i < results.length; i++){
                                        message = message + getString(R.string.test) + " " + (i + 1) + ": " + (results[i] > 0 ? results[i] + "ms" : getString(R.string.wrong)) + "\n";
                                        if(results[i] > 0) {
                                            average += results[i];
                                            count++;
                                        }
                                    }
                                    message = message+"\n"+getString(R.string.average)+": "+(count>0?(Math.round(average/count)):"-")+"ms";
                                    AlertDialog alertDialog = new AlertDialog.Builder(TestActivity.this, R.style.ThemeOverlay_AppCompat_Dark).create();
                                    alertDialog.setTitle(R.string.results);
                                    alertDialog.setMessage(message);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                    alertDialog.show();
                                }else{
                                    cooldown = generateRandomTimeValue();
                                }
                            }
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }.start();
    }

    private long generateRandomTimeValue(){
        long increase = minTimer+(long) (Math.random()*(maxTimer-minTimer)); //Random value between minTimer and maxTimer
        long current = System.currentTimeMillis();
        return current+increase;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sound)
            mediaPlayer.stop();
    }
}
