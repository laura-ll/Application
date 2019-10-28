package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends  android.app.ListActivity implements  Runnable{
    String[] data ={"one","two","three","four"};
    private static final String ACTIVITY_TAG="LogDemo";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);父类中包含一个页面布局
        List<String> list1 =new ArrayList<String>();
        int i;
        for(i=0;i<100;i++){
            list1.add("item"+i);
        }
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);//
       setListAdapter(adapter);
       Thread t=new Thread(this);
       t.start();
       handler=new Handler(){
           @Override
           public void handleMessage(Message msg){
               super.handleMessage(msg);
               if(msg.what==7){
               List<String>  list2=(List<String>) msg.obj;
                   ListAdapter adapter=new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_list_item_1,list2);//
                   setListAdapter(adapter);

               }


           }

       };
    }
    @Override
    public void run(){
        List<String> retList=new ArrayList<String>();
        Bundle bundle =new Bundle();
        try{

            Document doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            Elements tables=doc.getElementsByTag("table");

            Element table2=tables.get(1);
            Elements tds=table2.getElementsByTag("td");
            for(int t=0;t<tds.size();t+=8){
                Element td1=tds.get(t);//取第一列数据
                Element td2=tds.get(t+5);
                String str1=td1.text();
                String val=td2.text();
                Log.i(ACTIVITY_TAG,"run"+td1);

                retList.add(str1+"==>"+val);
                }

            } catch (IOException e){
            e.printStackTrace();
        }
        //获取msg对象用于返回主线程
        Message msg=handler.obtainMessage(7);
        // msg.what =5;
        msg.obj=retList;
        handler.sendMessage(msg);



    }
}
