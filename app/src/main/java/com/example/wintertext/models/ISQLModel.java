package com.example.wintertext.models;

import android.content.ContentValues;
import android.content.Context;

import com.example.wintertext.beans.GamePlayer;

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/19 18:58
 */
public interface ISQLModel {
    void insertData(String table,ContentValues values);
    void deleteData(String table,String selection,String selectionArgs);
    void updateData(String table,ContentValues values,String selection,String selectionArgs);
    <T extends Object>T getData(String table,T data,String key,String selection,String selectionArgs);
}
