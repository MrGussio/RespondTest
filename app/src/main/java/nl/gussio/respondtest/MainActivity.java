package nl.gussio.respondtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button testCountIncreaseButton, testCountDecreaseButton, minIncreaseButton, minDecreaseButton, maxIncreaseButton, maxDecreaseButton, startbutton;
    private CheckBox sightButton, soundButton;
    private EditText testCount, minCount, maxCount;
    private int countVal, minTimerVal, maxTimerVal;
    private boolean sightVal, soundVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testCountIncreaseButton = findViewById(R.id.testCountIncreaseButton);
        testCountDecreaseButton = findViewById(R.id.testCountDecreaseButton);
        minIncreaseButton = findViewById(R.id.minIncreaseButton);
        minDecreaseButton = findViewById(R.id.minDecreaseButton);
        maxIncreaseButton = findViewById(R.id.maxIncreaseButton);
        maxDecreaseButton = findViewById(R.id.maxDecreaseButton);
        sightButton = findViewById(R.id.sightButton);
        soundButton = findViewById(R.id.soundButton);
        testCount = findViewById(R.id.testCount);
        minCount = findViewById(R.id.minCount);
        maxCount = findViewById(R.id.maxCount);
        startbutton = findViewById(R.id.startButton);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        countVal = sharedPref.getInt("testCount", R.integer.testCountDefault);
        minTimerVal = sharedPref.getInt("minCount", R.integer.minCountDefault)*1000;
        maxTimerVal = sharedPref.getInt("maxCount", R.integer.maxCountDefault)*1000;
        sightVal = sharedPref.getBoolean("sight", false);
        soundVal = sharedPref.getBoolean("sound", false);

        testCount.setText(countVal+"");
        minCount.setText(minTimerVal/1000+"");
        maxCount.setText(maxTimerVal/1000+"");
        sightButton.setChecked(sightVal);
        soundButton.setChecked(soundVal);

        testCountIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int value = Integer.parseInt(testCount.getText().toString()) + 1;
                    testCount.setText(value + "");
                }catch (Exception e){
                    testCount.setText(R.integer.testCountDefault+"");
                }
            }
        });

        testCountDecreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int value = Integer.parseInt(testCount.getText().toString()) - 1;
                    testCount.setText(value > 0 ? value + "" : 1+"");
                }catch (Exception e){
                    testCount.setText(R.integer.testCountDefault+"");
                }
            }
        });

        minIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int value = Integer.parseInt(minCount.getText().toString()) + 1;
                    minCount.setText(value+"");
                }catch (Exception e){
                    minCount.setText(R.integer.minCountDefault+"");
                }
            }
        });

        minDecreaseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                    int value = Integer.parseInt(minCount.getText().toString()) - 1;
                    minCount.setText(value > 0 ? value+"" : 1+"");
                }catch(Exception e){
                    minCount.setText(R.integer.minCountDefault+"");
                }
            }
        });

        maxIncreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int value = Integer.parseInt(maxCount.getText().toString()) + 1;
                    maxCount.setText(value+"");
                }catch (Exception e){
                    maxCount.setText(R.integer.maxCountDefault+"");
                }
            }
        });

        maxDecreaseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try{
                    int value = Integer.parseInt(maxCount.getText().toString()) - 1;
                    maxCount.setText(value > 0 ? value+"" : 1+"");
                }catch(Exception e){
                    maxCount.setText(R.integer.maxCountDefault+"");
                }
            }
        });

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int testValue, minValue, maxValue;
                try {
                    testValue = Integer.parseInt(testCount.getText().toString());
                    minValue = Integer.parseInt(minCount.getText().toString());
                    maxValue = Integer.parseInt(maxCount.getText().toString());
                    if(testValue < 1){
                        Toast.makeText(MainActivity.this, R.string.countTooLowMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(minValue > maxValue){
                        Toast.makeText(MainActivity.this, R.string.minGreaterThanMaxMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!sightButton.isChecked() &! soundButton.isChecked()){
                        Toast.makeText(MainActivity.this, R.string.noModeSelectedMessage, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    testValue = R.integer.testCountDefault;
                    minValue = R.integer.minCountDefault;
                    maxValue = R.integer.maxCountDefault;
                }
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("testCount", testValue);
                editor.putInt("minCount", minValue);
                editor.putInt("maxCount", maxValue);
                editor.putBoolean("sound", soundButton.isChecked());
                editor.putBoolean("sight", sightButton.isChecked());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            editor.putInt("testCount", Integer.parseInt(testCount.getText().toString()));
            editor.putInt("minCount", Integer.parseInt(minCount.getText().toString()));
            editor.putInt("maxCount", Integer.parseInt(maxCount.getText().toString()));
            editor.putBoolean("sound", soundButton.isChecked());
            editor.putBoolean("sight", sightButton.isChecked());
            editor.commit();
        } catch (Exception e){
            editor.clear();
        }
    }

}