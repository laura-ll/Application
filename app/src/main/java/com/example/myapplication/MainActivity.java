package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {//对本窗口加上监听
     Button b1;
    Button b2;
    Button b3;
    Button b;
    TextView t;
    int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count);//获取布局

        b1= findViewById(R.id.button);
        b2= findViewById(R.id.button2);
        b3= findViewById(R.id.button3);
        b= findViewById(R.id.button9);

      /*  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//匿名类和尼玛对象
        btn.setOnClickListener(this);//this为当前对象，返回当前对象，调用onclick*/

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a+1;
                t.setText(a);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a+2;
                t.setText(a);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=a+3;
                t.setText(a);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=0;
                t.setText(a);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.i("hello","");//log 方法，logcat


    }//也可以在布局中直接在按钮上设置监听 onclick属性，在main中九无需使用接口可以直接定义一个public void 方法 参数为View v 然后赋值给button的属性
}
