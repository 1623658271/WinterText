package com.example.wintertext.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * description ： TODO:数据库操作
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/3 21:40
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_GAME = "create table Game("
            +"id integer primary key autoincrement, "
            +"name text, "
            +"life integer, "
            +"attack integer, "
            +"defense integer, "
            +"strike integer, "
            +"grade integer, "
            +"exc integer,"
            +"max_exc integer, "
            +"money integer, "
            +"steal integer)";
    public static final String CREATE_EQM = "create table Equipment("
            +"id integer primary key autoincrement, "
            +"eq_image integer, "
            +"eq_name text)";
    private Context context;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GAME);
        sqLiteDatabase.execSQL(CREATE_EQM);
        Toast.makeText(context, "数据库已创建", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context, "数据已更新", Toast.LENGTH_SHORT).show();
    }
}
