package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {


    private static final  int VERSION =1;
    private static final  String DB_NAME="myrate.db";

    public static final String TB_NAME="tb_rates";

    public DBHelper(Context context){

    super(context,name,factory,version);


    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE"+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");



    }
    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){



    }



}
