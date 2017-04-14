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
            if (l>-1){
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static Cursor selectAll(Context context,int start,int limit){
        SearchOpenHelper dbHelper = new SearchOpenHelper(context,
                "search",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select *from paint where oid>? and oid<? order by oid desc",new String[]{(start)+"",(start+limit+1)+""});
        Log.i("----",cursor.getCount()+"");
        Log.i("-------"+start,"=--"+(start+limit));
//        while (cursor.moveToNext()){
//            Log.e("cursor--oid-",""+cursor.getInt(cursor.getColumnIndex("oid")));
//            Log.e("cursor--content-",""+cursor.getString(cursor.getColumnIndex("content")));
//        }
        return cursor;
    }

    public static Boolean clearAll(Context context){
        SearchOpenHelper dbHelper = new SearchOpenHelper(context,
                "search",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("delete from paint");
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
