package com.example.wintertext.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wintertext.utilities.MyDatabaseHelper;

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/19 19:13
 */
public class SQLModel implements ISQLModel{
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private Cursor cursor;

    public SQLModel(Context context){
        this.context = context;
        dbHelper = new MyDatabaseHelper(context,"Game.db",null,1);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void insertData(String table,ContentValues values) {
        db.insert(table,null,values);
    }

    @Override
    public void deleteData(String table, String selection, String selectionArgs) {
        db.delete(table,selection, new String[]{selectionArgs});
    }

    @Override
    public void updateData(String table, ContentValues values, String selection, String selectionArgs) {
        db.update(table,values,selection,new String[]{selectionArgs});
    }

    @Override
    public <T> T getData(String table,T data,String key,String selection,String selectionArgs) {
        cursor = db.query(table,null,selection, new String[]{selectionArgs},null,null,null);
        return null;
    }
}
