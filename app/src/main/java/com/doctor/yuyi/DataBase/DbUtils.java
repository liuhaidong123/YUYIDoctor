package com.doctor.yuyi.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by wanyu on 2017/4/12.
 */

public class DbUtils {
    public static boolean insert(Context context,String content){
        SearchOpenHelper dbHelper = new SearchOpenHelper(context,
                "search",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("content",content);
        try{
            Long l=db.insert("paint",null,values);
            if (l>0){
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

            return false;
    }
    public static Cursor selectAll(Context context){
        SearchOpenHelper dbHelper = new SearchOpenHelper(context,
                "search",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select *from paint order by oid desc",null);
        return cursor;
    }

    public static Boolean clearAll(Context context){
        SearchOpenHelper dbHelper = new SearchOpenHelper(context,
                "search",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select *from paint order by oid desc",null);
        db.beginTransaction();
        try{
            db.delete("paint",null,null);
        }
        catch (Exception e){
            Log.e("删除所有搜索记录失败---","-------");
            e.printStackTrace();
            db.endTransaction();
            return false;
        }
        db.endTransaction();
        return true;
    }
}
