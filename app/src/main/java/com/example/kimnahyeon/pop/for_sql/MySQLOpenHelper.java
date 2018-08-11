package com.example.kimnahyeon.pop.for_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kimnahyeon on 2017. 7. 23..
 */

public class MySQLOpenHelper extends SQLiteOpenHelper {
    public MySQLOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String querySet = "create table bob ("+
                "sid integer primary key autoincrement, " +
                "isalways boolean, "+
                "back_image text);";

        String queryTask = "create table task (" +
                "tid integer primary key autoincrement, " +
                "content text, " +
                "isdone boolean);";

        db.execSQL(querySet);
        db.execSQL(queryTask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists bob;"+
                "drop table if exists task;";

        db.execSQL(query);

        onCreate(db);
    }
}

