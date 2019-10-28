package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Main3Activity extends AppCompatActivity {
   EditText t1;
    EditText t2;
    EditText t3;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
        t1=(EditText)findViewById(R.id.editText4);
        t2=(EditText) findViewById(R.id.editText5);
        t3=(EditText)findViewById(R.id.editText6);
        btn=(Button)findViewById(R.id.button10);
         Intent it =getIntent();
         float dollar=it.getFloatExtra("dollar",0.0f);
         float  euro=it.getFloatExtra("euro",0.0f);
         float won=it.getFloatExtra("won",0.0f);
         t1.setText(String.valueOf(dollar));
        t2.setText(String.valueOf(euro));
        t3.setText(String.valueOf(won));





    }

    public void listen(View v) {
        Log.i("hello","");//log 方法，logcat
       float newd=Float.parseFloat(t1.getText().toString());
        float newe=Float.parseFloat(t2.getText().toString());
        float neww=Float.parseFloat(t3.getText().toString());
        Intent in=getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("key_d",newd);
        bdl.putFloat("key_e",newe);
        bdl.putFloat("key_w",neww);
        in.putExtras(bdl);
        setResult(2,in);
        finish();
    }
}
