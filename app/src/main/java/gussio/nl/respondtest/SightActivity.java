package gussio.nl.respondtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SightActivity extends AppCompatActivity {

    private int count;
    private long cooldown=0L, resetCooldown=0L, timer=0L;
    private RelativeLayout layout;
    private long[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sight);

        layout = findViewById(R.id.sightLayout);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        count = sharedPref.getInt("testCount", 10);
        cooldown = generateRandomTimeValue();
        results = new long[count];
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timer > 0L){
                    long delta = System.currentTimeMillis()-timer;
                    Log.d("delta", Long.toString(delta));
                    results[results.length-count]=delta;
                    count--;
                    timer = 0L;
                    cooldown = 0L;
                    resetCooldown = System.currentTimeMillis()+500;
                    layout.setBackgroundColor(Color.GREEN);
                }else if(cooldown > 0L){
                    results[results.length-count]=-1;
                    count--;
                    timer = 0L;
                    cooldown = 0L;
                    resetCooldown = System.currentTimeMillis()+500;
                    layout.setBackgroundColor(Color.RED);
                }
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
                                timer = System.currentTimeMillis();
                                layout.setBackgroundColor(Color.BLUE);
                            }
                            if(resetCooldown > 0L && resetCooldown < System.currentTimeMillis()){//Wanneer de resetcooldown voorbij is
                                resetCooldown = 0L;
                                layout.setBackgroundColor(Color.GRAY);
                                if(count == 0){
                                    String message = "";
                                    for(int i = 0; i < results.length; i++){
                                        message = message+getString(R.string.test)+" "+(i+1)+": "+(results[i] > 0 ? results[i]+"": R.string.wrong+"")+"ms \n";
                                    }
                                    AlertDialog alertDialog = new AlertDialog.Builder(SightActivity.this, R.style.ThemeOverlay_AppCompat_Dark).create();
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
        long current = System.currentTimeMillis();
        long increase = 3000+(long) (Math.random()*(10000-3000)); //Random value between 3 and 10 seconds
        return current+increase;
    }
}
