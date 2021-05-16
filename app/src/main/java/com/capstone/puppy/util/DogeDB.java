package com.capstone.puppy.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;

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

        String sqlCreateTb1 = "CREATE TABLE IF NOT EXISTS  DOG_INFO (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT(20) NOT NULL, age INTEGER NOT NULL);";
        String sqlCreateTb2 = "CREATE TABLE IF NOT EXISTS  GPS_INFO (id INTEGER PRIMARY KEY AUTOINCREMENT, x REAL, y REAL, time TEXT not null DEFAULT (datetime('now', 'localtime')));";
        sqliteDB.execSQL(sqlCreateTb1);
        sqliteDB.execSQL(sqlCreateTb2);
    }
    public static void insertRecord(String dbName, String p_name, String p_age ){
        Log.i(TAG, "insertRecord()");
        String sql = "insert into " + dbName + " (name, age) values ('" + p_name + "','" + p_age + "');";
        Log.i(TAG, "sql : " + sql);
        sqliteDB.execSQL(sql);
    }

//    public static void selectRecord(){
//        Cursor c1 = sqliteDB.rawQuery
//    }

    public static  void insertGps(String x_pos, String y_pos){
        Log.i(TAG, "insertGps()");
        String sql = "insert into " + "GPS_INFO" + " (x, y) values ('" + x_pos +"','" + y_pos + "');";
        sqliteDB.execSQL(sql);

    }




}
