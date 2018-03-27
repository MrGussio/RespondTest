package gussio.nl.respondtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button testCountIncreaseButton, testCountDecreaseButton, minIncreaseButton, minDecreaseButton, maxIncreaseButton, maxDecreaseButton, sightButton, soundButton;
    private EditText testCount, minCount, maxCount;

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

        sightButton.setOnClickListener(new View.OnClickListener() {
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
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), SightActivity.class);
                startActivity(intent);
            }
        });

        soundButton.setOnClickListener(new View.OnClickListener() {
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
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), SoundActivity.class);
                startActivity(intent);
            }
        });
    }





}