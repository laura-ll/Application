package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {
    float rate=0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        String title=getIntent().getStringExtra("title");
        getIntent().getFloatExtra("rate",0f);
        ((TextView)findViewById(R.id.title2)).setText(title);

    }
}
