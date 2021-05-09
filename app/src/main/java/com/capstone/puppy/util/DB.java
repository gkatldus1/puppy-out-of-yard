package com.capstone.puppy.util;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class DB {
    SQLiteDatabase sqliteDB = null;

    public void makeTable(){

      try{
          sqliteDB = SQLiteDatabase.openOrCreateDatabase("Doge.db",MODE_WORLD_WRITEABLE, null);
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
