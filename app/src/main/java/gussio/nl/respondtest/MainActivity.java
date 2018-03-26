package gussio.nl.respondtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button increaseButton, decreaseButton, sightButton, soundButton;
    private EditText testCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        sightButton = findViewById(R.id.sightButton);
        soundButton = findViewById(R.id.soundButton);
        testCount = findViewById(R.id.testCount);

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int value = Integer.parseInt(testCount.getText().toString()) + 1;
                    testCount.setText(value + "");
                }catch (Exception e){
                    testCount.setText(0+"");
                }
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int value = Integer.parseInt(testCount.getText().toString()) - 1;
                    testCount.setText(value > 0 ? value + "" : 1+"");
                }catch (Exception e){
                    testCount.setText(1+"");
                }
            }
        });

        sightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value;
                try {
                    value = Integer.parseInt(testCount.getText().toString());
                    if(value < 1){
                        Toast.makeText(MainActivity.this, R.string.countTooLowMessage, Toast.LENGTH_LONG);
                        return;
                    }
                }catch (Exception e){
                    value = 10;
                }
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("testCount", value);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), SightActivity.class);
                startActivity(intent);
            }
        });
    }





}