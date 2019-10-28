package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Main2Activity extends AppCompatActivity implements Runnable {
  EditText in;
  TextView  out;
  Button btn1 ;
  Button btn2 ;
  Button btn3;
    String str;

    private float dollar=0.1f;
    private float euro=0.2f;
    private float won=0.3f;
    private String updateDate="";

    Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Document doc= Jsoup.connect("http://en.wikipedia.org/").get();
        //Elements newsHeadlines =doc.select("#mp-itn b a");
        //for(Element heafline: newsHeadlines){

        //}


        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans);
        in=(EditText)findViewById(R.id.editText);
        out=(TextView)findViewById(R.id.textView5);
        btn1=findViewById(R.id.button4);
        btn2=findViewById(R.id.button5);
        btn3=findViewById(R.id.button6);
        Intent intent = new Intent(this,Main3Activity.class);

       //获取sp内的数据
        SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);//第一个参数是字符串，第二个是访问权限
        //(myrate.xml)
        dollar =sharedPreferences.getFloat("dollar",0.0f);
        euro =sharedPreferences.getFloat("euro",0.0f);
        won =sharedPreferences.getFloat("won",0.0f);
        updateDate=sharedPreferences.getString("update_date","");

        //获取当前系统时间
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写m为分钟数
        final String todayStr=sdf.format(today);
        //判断时间
        if(!todayStr.equals(updateDate)){

            //开启子线程
            Thread t = new Thread(this);
            t.start();//子线程开始运行


        }


        handler =new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==5){

                    Bundle str=(Bundle)msg.obj;
                    dollar=str.getFloat("dollar",0.0f);
                    euro=str.getFloat("euro",0.0f);
                    won=str.getFloat("won",0.0f);
                    //j将日期保存到sp
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("dollar",dollar);
                    editor.putFloat("euro",euro);
                    editor.putFloat("won",won);
                    editor.putString("update_date",todayStr);
                    editor.commit();
                    Toast.makeText(Main2Activity.this,"汇率已更新",Toast.LENGTH_SHORT).show();


                }

            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//自动调用
        getMenuInflater().inflate(R.menu.abc,menu);
        return true;//启动menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
           openConfig();
            //事件处理代码
        }
        if(item.getItemId()==R.id.menu_list){

            Intent config=new Intent(this,ListActivity.class);

            startActivityForResult(config,3);

          /*  //写入数据库
            RateItem item1=new RateItem("aaa","333");
            RateManager manager=new RateManager(this);
            manager.add(item1);
            manager.add(new RateItem("bbb","23.5"));
            //查询
            List<RateItem> testList=manager.listAll();
            for(RateItem i:testList){

            }
            */
          //事件处理代码
        }

        return super.onOptionsItemSelected(item);
    }


    public void listen(View btn){
        str=in.getText().toString();
        float k=0;
        if(str.length()>0){
               k =Float.parseFloat(str);
        }else{//提示信息
            Toast.makeText(this,"please input",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.button4) {
            k = k * dollar;
        }
        if(btn.getId()==R.id.button5) {
            k = k * euro;
        }
        if(btn.getId()==R.id.button6) {
            k = k * won;
        }

     String s=""+k;
       out.setText(s);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==2){


            Bundle  bdl=data.getExtras();
            dollar=bdl.getFloat("key_d",0.1f);
            euro=bdl.getFloat("key_e",0.2f);
            won=bdl.getFloat("key_w",0.3f);
            //将数据保存到sp
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar",dollar);
            editor.putFloat("euro",euro);
            editor.putFloat("won",won);
            editor.commit();

        }
    }
    public void openConfig() {

        Intent config = new Intent(this, Main3Activity.class);
        config.putExtra("dollar", dollar);
        config.putExtra("euro", euro);
        config.putExtra("won", won);
        startActivityForResult(config, 1);//确定是谁返回的数据


    }
        public void openOne(View btn){
       openConfig();

    }
   @Override
    public  void run(){
        //用于保存获取的汇率
       Bundle bundle ;

               //获取网络数据

             /*  try {
                   URL url = new URL("http://www.usd-cny.com");
                   HttpURLConnection http = (HttpURLConnection) url.openConnection();
                   InputStream in=http.getInputStream();
                   String html=inputStream2String(in);

               }catch (IOException e){
                   e.printStackTrace();
               } */
         bundle=getBank();
       //获取msg对象用于返回主线程
            Message msg=handler.obtainMessage(5);
             // msg.what =5;
              msg.obj=bundle;
              handler.sendMessage(msg);

           }

    private Bundle getBank() {
        Bundle bundle=new Bundle();
        Document doc=null;
        try{
           doc= Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
           Elements tables=doc.getElementsByTag("table");
           int k=1;

            Element table6=tables.get(1);
            Elements tds=table6.getElementsByTag("td");
            for(int t=0;t<tds.size();t+=8){
                Element td1=tds.get(t);//取第一列数据
                Element td2=tds.get(t+5);
                String str1=td1.html();
                String val=td2.html();
                if("美元".equals(str1)){

                    bundle.putFloat("dollar",100f/Float.parseFloat(val));

                }
                if("欧元".equals(str1)){

                    bundle.putFloat("euro",100f/Float.parseFloat(val));

                }
                if("韩国元".equals(str1)){

                    bundle.putFloat("won",100f/Float.parseFloat(val));

                }

            }
        //bundle中保存所需要的汇率
        }catch (IOException e){
            e.printStackTrace();
        }
        return bundle;
    }


    private  String  inputStream2String(InputStream inputStream)throws  IOException{
      final int  bufferSize =1024;
      final char[] buffer =new char [bufferSize];
      final StringBuilder out=new StringBuilder();
      Reader in=new InputStreamReader(inputStream,"gb2312");

      for( ; ;){
          int rsz =in.read(buffer,0,buffer.length);
          if(rsz<0)
              break;
          out.append(buffer,0,rsz);
      }
       return out.toString();



   }
}
