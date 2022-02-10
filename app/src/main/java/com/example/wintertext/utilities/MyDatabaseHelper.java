package com.example.wintertext.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
            +"steal integer)";
    private Context context;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GAME);
        Toast.makeText(context, "数据库已创建", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Toast.makeText(context, "数据已更新", Toast.LENGTH_SHORT).show();
    }
}
