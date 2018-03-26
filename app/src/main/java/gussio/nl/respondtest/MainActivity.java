package gussio.nl.respondtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button increaseButton, decreaseButton;
    private EditText testCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
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
    }





}
