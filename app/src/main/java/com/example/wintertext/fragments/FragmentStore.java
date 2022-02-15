package com.example.wintertext.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.adapters.EquipmentRvAdapter;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.example.wintertext.utilities.SharedGamePlayerData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * description ： TODO:商店界面碎片
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 18:54
 */
public class FragmentStore extends Fragment {
    private View view;
    private TextView name1,name2,name3,name4,name5,name6;
    private TextView need1,need2,need3,need4,need5,need6,my;
    private int n1,n2,n3,n4,n5,n6,my_money;
    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;
    private EquipmentRvAdapter adapter;
    private String na1,na2,na3,na4,na5,na6;
    private List<String> equipments;
    private List<Integer> resources;
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6;
    private MaterialButton button1,button2,button3,button4,button5,button6;
    private List<MaterialButton> buttons = new ArrayList<>();
    private List<ImageButton> imageButtons = new ArrayList<>();
    private LinearLayout linearLayout;
    private GamePlayer gamePlayer;
    private int life,attack,defense,strike,steal;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String TAG = "123";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//初始化控件
        initData();//初始化数据
        updateMoney();//动态更新money
        initImageButtonEvent();//点击事件
        initButtonEvent();//点击事件
        initEvent();
    }

    //按键的点击事件
    private void initEvent() {
        button1.setOnClickListener(view1 -> {
            if(my_money > n1 || my_money == n1) {
                ClickBtnBought("多兰剑",true );
            }else{
                ClickBtnBought("多兰剑",false);
            }
        });
        button2.setOnClickListener(view1 -> {
            if(my_money > n2 || my_money == n2) {
                ClickBtnBought("锁子甲",true );
            }else{
                ClickBtnBought("锁子甲",false);
            }
        });
        button3.setOnClickListener(view1 -> {
            if(my_money > n3 || my_money == n3) {
                ClickBtnBought("巨人腰带",true );
            }else{
                ClickBtnBought("巨人腰带",false);
            }
        });
        button4.setOnClickListener(view1 -> {
            if(my_money > n4 || my_money == n4) {
                ClickBtnBought("不朽盾弓",true );
            }else{
                ClickBtnBought("不朽盾弓",false);
            }
        });
        button5.setOnClickListener(view1 -> {
            if(my_money > n5 || my_money == n5) {
                ClickBtnBought("无尽之刃",true );
            }else{
                ClickBtnBought("无尽之刃",false);
            }
        });
        button6.setOnClickListener(view1 -> {
            if(my_money > n6 || my_money == n6) {
                ClickBtnBought("饮血剑",true );
            }else{
                ClickBtnBought("饮血剑",false);
            }
        });
    }

    //点击购买后的事件
    private void ClickBtnBought(String na,boolean afford){
        if(afford) {
            Toast.makeText(getContext(),"购买成功!",Toast.LENGTH_SHORT).show();
            if (na.equals("多兰剑")) {
                afterBought("多兰剑",R.drawable.duolan,10,0,0,3,0,450);
            } else if (na.equals("锁子甲")) {
                afterBought("锁子甲",R.drawable.suozijia,0,50,0,0,0,450);
            } else if (na.equals("巨人腰带")) {
                afterBought("巨人腰带",R.drawable.juren,0,0,0,0,480,900);
            } else if (na.equals("不朽盾弓")) {
                afterBought("不朽盾弓",R.drawable.eqm_buxiudungong,50,0,10,12,0,3400);
            } else if (na.equals("无尽之刃")) {
                afterBought("无尽之刃",R.drawable.wujingzhiren,70,0,20,0,0,3400);
            } else if (na.equals("饮血剑")) {
                afterBought("饮血剑",R.drawable.yinxiejian,55,0,10,20,0,3400);
            }
        }else {
            Toast.makeText(getContext(),"金币不足,无法购买!",Toast.LENGTH_SHORT).show();
        }
    }

    //更新属性,装备栏等的方法
    private void afterBought(String name, int resource,int add_attack,int add_defense,int add_strike,int add_steal,int add_life,int cast_money){
        equipments.add(name);
        resources.add(resource);
        recyclerView.setAdapter(adapter);
        my_money -= cast_money;
        ContentValues values = new ContentValues();

        values.put("money",my_money);

        if(add_attack!=0){
            attack+=add_attack;
            values.put("attack",attack);
        }
        if(add_defense!=0){
            defense+=add_defense;
            values.put("defense",defense);
        }
        if(add_strike!=0 && (strike+add_attack < 100 || strike+add_attack == 100)){
            strike+=add_strike;
            values.put("strike",strike);
        }
        if(add_steal!=0 && (steal+add_steal < 100 || steal+add_steal == 100)){
            steal+=add_steal;
            values.put("steal",steal);
        }
        if(add_life!=0){
            life+=add_life;
            values.put("life",life);
        }
        my.setText(String.valueOf(my_money));
        db.update("Game",values,"name = ?",new String[]{"yasuo"});
        values.clear();
        values.put("eq_name",name);
        values.put("eq_image",resource);
        Log.d(TAG, "afterBought: "+resource);
        db.insert("Equipment",null,values);
        values.clear();
        Intent intent = new Intent("com.example.wintertext.update_setting");
        getActivity().sendBroadcast(intent);
    }

    //初始化数据
    @SuppressLint("Range")
    private void initData() {
        dbHelper = new MyDatabaseHelper(getContext(),"Game.db",null,1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Game",null,"name = ?",new String[]{"yasuo"},null,null,null);
        if(cursor.moveToFirst()){
            my_money = cursor.getInt(cursor.getColumnIndex("money"));
            life = cursor.getInt(cursor.getColumnIndex("life"));
            attack = cursor.getInt(cursor.getColumnIndex("attack"));
            defense = cursor.getInt(cursor.getColumnIndex("defense"));
            strike = cursor.getInt(cursor.getColumnIndex("strike"));
            steal = cursor.getInt(cursor.getColumnIndex("steal"));
        }
        cursor.close();
        my.setText(String.valueOf(my_money));
        Cursor cursor2 = db.query("Equipment",null,null,null,null,null,null);
        if(cursor2.moveToFirst()){
            do {
                equipments.add(cursor2.getString(cursor2.getColumnIndex("eq_name")));
                resources.add(cursor2.getInt(cursor2.getColumnIndex("eq_image")));
            }while (cursor2.moveToNext());
            recyclerView.setAdapter(adapter);
            cursor2.close();
        }
    }

    //"我的装备"悬浮按钮的点击事件
    private void initButtonEvent() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout.getVisibility()==View.VISIBLE){
                    linearLayout.setVisibility(View.INVISIBLE);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //商店装备的图形按钮的点击事件
    private void initImageButtonEvent() {
        for(int i=0;i<6;i++){
            int finalI = i;
            imageButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(buttons.get(finalI).getVisibility() == View.VISIBLE){
                        buttons.get(finalI).setVisibility(View.GONE);
                    }else{
                        buttons.get(finalI).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    //动态更新money的一个方法
    public void updateMoney(){
        SharedGamePlayerData sharedGamePlayerData = new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(SharedGamePlayerData.class);
        sharedGamePlayerData.getData().observe(this,item->{
            initMoney();
            my_money+=item.getMoney();
            my.setText(String.valueOf(my_money));
            updateMoneyToSQL();
        });
    }

    //从数据库中再次初始化money
    @SuppressLint("Range")
    private void initMoney(){
        Cursor cursor = db.query("Game",null,"name = ?",new String[]{"yasuo"},null,null,null);
        if(cursor.moveToFirst()){
            my_money = cursor.getInt(cursor.getColumnIndex("money"));
        }
    }

    //更新数据库中的money数值
    private void updateMoneyToSQL(){
        ContentValues values = new ContentValues();
        values.put("money",my_money);
        db.update("Game",values,"name = ?",new String[]{"yasuo"});
    }

    //初始化控件操作
    private void initView() {
        //初始化
        imageButton1 = view.findViewById(R.id.eqm1);
        imageButton2 = view.findViewById(R.id.eqm2);
        imageButton3 = view.findViewById(R.id.eqm3);
        imageButton4 = view.findViewById(R.id.eqm4);
        imageButton5 = view.findViewById(R.id.eqm5);
        imageButton6 = view.findViewById(R.id.eqm6);
        button1 = view.findViewById(R.id.btn_buy_eqm1);

        button2 = view.findViewById(R.id.btn_buy_eqm2);
        button3 = view.findViewById(R.id.btn_buy_eqm3);
        button4 = view.findViewById(R.id.btn_buy_eqm4);
        button5 = view.findViewById(R.id.btn_buy_eqm5);
        button6 = view.findViewById(R.id.btn_buy_eqm6);

        actionButton = view.findViewById(R.id.fab_home);
        linearLayout = view.findViewById(R.id.eqm_home);
        recyclerView = view.findViewById(R.id.home_recyclerview);
        equipments = new ArrayList<>();
        resources = new ArrayList<>();
        adapter = new EquipmentRvAdapter(equipments,resources);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);


        gamePlayer = new GamePlayer();

        //添加到List中
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        imageButtons.add(imageButton1);
        imageButtons.add(imageButton2);
        imageButtons.add(imageButton3);
        imageButtons.add(imageButton4);
        imageButtons.add(imageButton5);
        imageButtons.add(imageButton6);

        name1 = view.findViewById(R.id.eqm1_name);
        name2 = view.findViewById(R.id.eqm2_name);
        name3 = view.findViewById(R.id.eqm3_name);
        name4 = view.findViewById(R.id.eqm4_name);
        name5 = view.findViewById(R.id.eqm5_name);
        name6 = view.findViewById(R.id.eqm6_name);

        need1 = view.findViewById(R.id.need_money_eqm1);
        need2 = view.findViewById(R.id.need_money_eqm2);
        need3 = view.findViewById(R.id.need_money_eqm3);
        need4 = view.findViewById(R.id.need_money_eqm4);
        need5 = view.findViewById(R.id.need_money_eqm5);
        need6 = view.findViewById(R.id.need_money_eqm6);

        my = view.findViewById(R.id.my_money);
        //获取装备价格
        n1 = Integer.parseInt(need1.getText().toString());
        n2 = Integer.parseInt(need2.getText().toString());
        n3 = Integer.parseInt(need3.getText().toString());
        n4 = Integer.parseInt(need4.getText().toString());
        n5 = Integer.parseInt(need5.getText().toString());
        n6 = Integer.parseInt(need6.getText().toString());
        //获取装备名称
        na1 = name1.getText().toString();
        na2 = name2.getText().toString();
        na3 = name3.getText().toString();
        na4 = name4.getText().toString();
        na5 = name5.getText().toString();
        na6 = name6.getText().toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store,container,false);
        return view;
    }

}
