package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2 extends android.app.ListActivity implements Runnable,AdapterView.OnItemClickListener{
    private String TAG;
    Handler handler;
    private ArrayList<HashMap<String, String>> listItem; //存放图片文字信息
    private SimpleAdapter listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initListView();
        //MyAdapter myAdapter = new MyAdapter(this, R.layout.item, listItem);
        //this.setListAdapter(myAdapter);
       this.setListAdapter(listadapter);
        Thread t = new Thread();
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 7) {
                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                    //生成适配器的Item和动态数组对应元素
                    listadapter = new SimpleAdapter(MyList2.this, list2, R.layout.item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail});
                          setListAdapter(listadapter);

                }
                super.handleMessage(msg);
            }
        };
    /*    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }); */
      getListView().setOnItemClickListener(this);


    }



        private void initListView () {
            listItem = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < 10; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", "Rate:" + i);
                map.put("ItemDetail", "detail" + i);
                listItem.add(map);

            }
       //生成适配器的Item和动态数组对应元素
        listadapter =new SimpleAdapter(this,listItem,R.layout.item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});

        }
         @Override
        public void run () {

            List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
            Document doc;
            try {
                doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
                Elements tables = doc.getElementsByTag("table");

                Element table2 = tables.get(1);
                Elements tds = table2.getElementsByTag("td");
                for (int t = 0; t < tds.size(); t += 8) {
                    Element td1 = tds.get(t);//取第一列数据
                    Element td2 = tds.get(t + 5);
                    String str1 = td1.text();
                    String val = td2.text();
                    Log.i(TAG,str1);
                    Log.i(TAG,val);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", str1);
                    map.put("ItemDetail", val);
                    retList.add(map);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取msg对象用于返回主线程
            Message msg = handler.obtainMessage(7);
            // msg.what =5;
            msg.obj = retList;
            handler.sendMessage(msg);

        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> map=(HashMap<String, String>) getListView().getItemAtPosition(position);//获得当前行对应数据项
         String titleStr=   map.get("ItemTitle");
        String  detailStr=map.get("ItemDetail");

        TextView title=(TextView) view.findViewById(R.id.itemTitle);
        TextView detail=(TextView)view.findViewById(R.id.itemDetail);
        String title2=String.valueOf(title);
        String detail2=String.valueOf(detail);

        //打开新的页面。传入参数
            Intent rate=new Intent(this,RateActivity.class);
            rate.putExtra("title",titleStr);
            rate.putExtra("rate",Float.parseFloat(detailStr));
            startActivity(rate);
        }

         }
