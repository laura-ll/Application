package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyList extends AppCompatActivity {
    List<String> data =new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        GridView listView=(GridView) findViewById(R.id.mylist);
       for(int i=0;i<10;i++){
           data.add("item"+i);

       }
        ListAdapter adapter=new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,data);//
        listView.setAdapter(adapter);

    }
}
