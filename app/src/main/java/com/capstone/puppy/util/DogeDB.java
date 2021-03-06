package com.capstone.puppy.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.capstone.puppy.PuppyInfo.GPSInfo;
import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.io.File;
import java.util.ArrayList;

public class DogeDB{
    public final static String TAG = "DogeDB";
    static Context context;


    public static void setDogeContext(Context mcontext){
        context = mcontext;
    }

    static SQLiteDatabase sqliteDB = null;

    public static void makeTable(){
        try{
            File db_file = context.getDatabasePath("Doge.db");
            sqliteDB = SQLiteDatabase.openOrCreateDatabase(db_file, null);
            Log.i(TAG, db_file.getParent());
        } catch(SQLiteException e)
        {
            e.printStackTrace();
        }

        String sqlCreateTb1 = "CREATE TABLE IF NOT EXISTS  DOG_INFO (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT(20) NOT NULL, age TEXT(20) NOT NULL);";
        String sqlCreateTb2 = "CREATE TABLE IF NOT EXISTS  GPS_INFO (id INTEGER PRIMARY KEY AUTOINCREMENT, x double, y double, time TEXT not null DEFAULT (datetime('now', 'localtime')));";
        sqliteDB.execSQL(sqlCreateTb1);
        sqliteDB.execSQL(sqlCreateTb2);
    }

    public static void updateRecord(String p_name, String p_age,String url, int id){
        Log.i(TAG, "updateRecord()");
        ContentValues values = new ContentValues();
        values.put("name", p_name);
        values.put("age", p_age);
        sqliteDB.update("DOG_INFO",values, "id=?", new String[]{Integer.toString(id)});
    }


    public static void insertRecord(String dbName, String p_name, String p_age, String url ){
        Log.i(TAG, "insertRecord()");
        String sql = "insert into " + dbName + " (name, age) values ('" + p_name + "','" + p_age + "');";
        Log.i(TAG, "sql : " + sql);
        sqliteDB.execSQL(sql);
    }

    public static ArrayList<PuppyInfo> selectDogRecord(){
        Cursor cursor= sqliteDB.rawQuery("select * from DOG_INFO", null);
        ArrayList<PuppyInfo> puppys = new ArrayList<>();
        cursor.moveToFirst();
        //puppys.add(new PuppyInfo(cursor.getString(1), cursor.getString(2), ""));
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String url = "";
            String name = cursor.getString(1);
            String age = cursor.getString(2);
            PuppyInfo puppy = new PuppyInfo(id, name, age, url);
            Log.i(TAG, "id:" + id + " name:" + name + " age:" + age + " url:"+url);
            puppys.add(puppy);
        }
        return puppys;
    }

    public static ArrayList<GPSInfo> selectGpsRecord(){

        Cursor ID_NUM = sqliteDB.rawQuery("select count(id) from GPS_INFO", null);
        ID_NUM.moveToFirst();
        int id_num = ID_NUM.getInt(0);
        int start = id_num - 10;
        id_num = 10;
        start = 1;

        Cursor cursor = sqliteDB.rawQuery("select * from GPS_INFO where id >="+ start + " and id<=" + id_num , null);
        cursor.moveToFirst();
        ArrayList<GPSInfo> gps = new ArrayList<>();

        do{
            gps.add(new GPSInfo(cursor.getDouble(1), cursor.getDouble(2), cursor.getString(3)));
        }while(cursor.moveToNext());

        return gps;
    }

    public static  void insertGps(double x_pos, double y_pos){
        Log.i(TAG, "insertGps()");
        String sql = "insert into " + "GPS_INFO" + " (x, y) values ('" + x_pos +"','" + y_pos + "');";
        sqliteDB.execSQL(sql);

    }


}
