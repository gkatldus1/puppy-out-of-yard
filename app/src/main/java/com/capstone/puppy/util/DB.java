package com.capstone.puppy.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;

import static android.os.ParcelFileDescriptor.MODE_WORLD_WRITEABLE;

public class DB {
    public final static String TAG = "DB";
    Context context;

    public DB(Context context){
        this.context = context;
    }

    SQLiteDatabase sqliteDB = null;

    public void makeTable(){

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
}
