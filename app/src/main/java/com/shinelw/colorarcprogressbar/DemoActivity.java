package com.shinelw.colorarcprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shinelw.library.ColorArcProgressBar;


public class DemoActivity extends AppCompatActivity {
    private Button button;
    private EditText text;
    private ColorArcProgressBar bar;
    Handler mhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            bar.setCurrentValues((float) (Math.random()* 200));
            mhandle.sendEmptyMessageDelayed(1, 1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        bar = (ColorArcProgressBar) findViewById(R.id.bar);
        bar.setMaxValues(200);
        bar.setCurrentValues(0);
        button = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setCurrentValues(Float.valueOf(text.getText().toString()));
            }
        });


    }
}
