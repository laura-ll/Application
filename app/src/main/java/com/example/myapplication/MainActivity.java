package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {//对本窗口加上监听
     EditText in;
     String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc);//获取布局
        in=findViewById(R.id.t2);//找布局中的控件(类型和i要一致) (局部变量在onclick 无法使用)

         str =in.getText().toString();
        Button btn =findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//匿名类和尼玛对象
        btn.setOnClickListener(this);//this为当前对象，返回当前对象，调用onclick
    }

    @Override
    public void onClick(View v) {
        Log.i("hello",str);//log 方法，logcat

    }//也可以在布局中直接在按钮上设置监听 onclick属性，在main中九无需使用接口可以直接定义一个public void 方法 参数为View v 然后赋值给button的属性
}
