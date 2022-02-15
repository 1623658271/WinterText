package com.example.wintertext.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.wintertext.R;
import com.example.wintertext.activities.MainActivity;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.example.wintertext.utilities.SharedGamePlayerData;

/**
 * description ： TODO:属性界面
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 18:45
 */
public class FragmentSetting extends Fragment {
    private TextView life,attack,defense,strike,steal,grade;
    private ProgressBar exc_progressbar;
    private int i_life,i_attack,i_defense,i_strike,i_steal;
    private int a_life,a_attack,a_defense,a_strike,a_steal,dog_grade,dog_attack;
    private int i_exc,i_max_exc,i_grade;
    private int get_exc;
    private SQLiteDatabase db;
    private View view;
    private MyDatabaseHelper myDatabaseHelper;
    private String TAG = "123";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        updateExc();
        initBroadCastReceiver();
    }

    private void initBroadCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.wintertext.update_setting");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initData();
            }
        };
        getActivity().registerReceiver(receiver,intentFilter);
    }

    private void updateExc() {
        SharedGamePlayerData sharedGamePlayerData = new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(SharedGamePlayerData.class);
        sharedGamePlayerData.getData().observe(this,item->{
            get_exc = item.getExc();
            i_exc = exc_progressbar.getProgress();
            i_exc += get_exc;
            Log.d(TAG, "updateExc: "+i_exc);
            if(i_max_exc > i_exc){
                exc_progressbar.setProgress(i_exc);
                ContentValues values = new ContentValues();
                values.put("exc",i_exc);
                db.update("Game",values,"name = ?",new String[]{"yasuo"});
            }else{
                level_up();
            }
        });
    }

    //升级
    private void level_up() {
        i_grade++;
        i_exc -= i_max_exc;
        i_life += 50;
        i_attack += 10;
        i_defense += 10;
        i_max_exc += 10;
        grade.setText(String.valueOf(i_grade));
        exc_progressbar.setProgress(i_exc);
        exc_progressbar.setMax(i_max_exc);

        a_life += 40;
        a_attack += 10;
        a_defense += 12;
        a_steal += 2;
        a_strike += 2;

        dog_attack += 5;
        dog_grade += 1;
        ContentValues values = new ContentValues();
        values.put("life",i_life);
        values.put("grade",i_grade);
        values.put("exc",i_exc);
        values.put("max_exc",i_max_exc);
        values.put("attack",i_attack);
        values.put("defense",i_defense);
        db.update("Game",values,"name = ?",new String[]{"yasuo"});
        values.clear();

        values.put("life",a_life);
        values.put("attack",a_attack);
        values.put("defense",a_defense);
        values.put("strike",a_strike);
        values.put("steal",a_steal);
        db.update("Game",values,"name = ?",new String[]{"yong_en"});
        values.clear();

        values.put("attack",dog_attack);
        values.put("grade",dog_grade);
        db.update("Game",values,"name = ?",new String[]{"pao_che"});

        initData();
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

        //永恩肯定得跟着升级,所以先取出数据
        Cursor cursor2 = db.query("Game",null,"name = ?",new String[]{"yong_en"},null,null,null);
        if(cursor2.moveToFirst()) {
            //遍历cursor对象，取出数据
            a_life = cursor2.getInt(cursor2.getColumnIndex("life"));
            a_defense = cursor2.getInt(cursor2.getColumnIndex("defense"));
            a_attack = cursor2.getInt(cursor2.getColumnIndex("attack"));
            a_strike = cursor2.getInt(cursor2.getColumnIndex("strike"));
            a_steal = cursor2.getInt(cursor2.getColumnIndex("steal"));
        }
        //炮车同理
        cursor.close();
        Cursor cursor3 = db.query("Game",null,"name = ?",new String[]{"pao_che"},null,null,null);
        if(cursor3.moveToFirst()) {
            //遍历cursor对象，取出数据
            dog_grade = cursor3.getInt(cursor3.getColumnIndex("grade"));
            dog_attack = cursor3.getInt(cursor3.getColumnIndex("attack"));
        }
        cursor.close();
    }
}
