package com.example.wintertext.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;
import com.example.wintertext.activities.MainActivity;
import com.example.wintertext.utilities.MyDatabaseHelper;

public class FragmentSetting extends Fragment {
    private TextView life,attack,defense,strike,steal,grade;
    private ProgressBar exc_progressbar;
    private int i_life,i_attack,i_defense,i_strike,i_steal;
    private int i_exc,i_max_exc,i_grade;
    private SQLiteDatabase db;
    private View view;
    private MyDatabaseHelper myDatabaseHelper;
    private int get_exc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

    public void upData(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        if(bundle == null){
            Toast.makeText(getContext(), "没有数据更新", Toast.LENGTH_SHORT).show();
        }else
        {
            get_exc = bundle.getInt("get_exc");
            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        exc_progressbar = view.findViewById(R.id.exc_progressbar);

        life = view.findViewById(R.id.setting_number_life);
        attack = view.findViewById(R.id.setting_number_attack);
        defense = view.findViewById(R.id.setting_number_defense);
        strike = view.findViewById(R.id.setting_number_strike);
        steal = view.findViewById(R.id.setting_number_steal);
        grade = view.findViewById(R.id.number_grade);

        i_exc = exc_progressbar.getProgress();
        i_max_exc = exc_progressbar.getMax();

        myDatabaseHelper = new MyDatabaseHelper(getContext(),"Game.db",null,1);
        db = myDatabaseHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    private void initData() {
        Cursor cursor = db.query("Game",null,"name = ?",new String[]{"yasuo"},null,null,null);
        if(cursor.moveToFirst()) {
                //遍历cursor对象，取出数据
                i_life = cursor.getInt(cursor.getColumnIndex("life"));
                i_defense = cursor.getInt(cursor.getColumnIndex("defense"));
                i_attack = cursor.getInt(cursor.getColumnIndex("attack"));
                i_strike = cursor.getInt(cursor.getColumnIndex("strike"));
                i_steal = cursor.getInt(cursor.getColumnIndex("steal"));
                i_grade = cursor.getInt(cursor.getColumnIndex("grade"));
                i_exc = cursor.getInt(cursor.getColumnIndex("exc"));
                i_max_exc = cursor.getInt(cursor.getColumnIndex("max_exc"));
        }
        cursor.close();
        //设值
        life.setText(String.valueOf(i_life));
        defense.setText(String.valueOf(i_defense));
        attack.setText(String.valueOf(i_attack));
        strike.setText(String.valueOf(i_strike));
        steal.setText(String.valueOf(i_steal));
        grade.setText(String.valueOf(i_grade));
        exc_progressbar.setProgress(i_exc);
        exc_progressbar.setMax(i_max_exc);
    }
}
