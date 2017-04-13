package com.doctor.yuyi.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanyu on 2017/4/12.
 */

public class SearchOpenHelper extends SQLiteOpenHelper{

    public SearchOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "search", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table paint( oid Integer primary key,content varchar(40),count Integer AUTOINCREMENT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
