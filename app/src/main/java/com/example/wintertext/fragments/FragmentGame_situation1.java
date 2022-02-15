package com.example.wintertext.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.adapters.MsgRecyclerViewAdapter;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.beans.Msg;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.example.wintertext.utilities.SharedGamePlayerData;

import java.util.ArrayList;
import java.util.List;

/**
 * description ： TODO:游戏界面的一个碎片，用于展示战后情况
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:35
 */
public class FragmentGame_situation1 extends Fragment {
    private List<Msg> msgList;
    private TextView initView;
    private View view;
    private RecyclerView recyclerView;
    private MsgRecyclerViewAdapter adapter;
    private MyDatabaseHelper dpHelper;
    private SQLiteDatabase db;
    private String TAG = "123";
    private String winner;
    private int final_A_life;
    private int final_B_life;
    private int all_dogface_hurt_to_A;
    private int all_dogface_hurt_to_B;
    private int all_A_hurt_to_B;
    private int all_B_hurt_to_A;
    private int all_A_hui_fu;
    private int all_B_hui_fu;
    private int get_exc;
    private int game_number = 1;
    private int get_money;
    private int kill_dogface = 0;
    private SharedGamePlayerData sharedGamePlayerData;
    private GamePlayer gamePlayer;
    private MediaPlayer win,dead,pin;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initBroadcastListener();
        initData();
    }

    //设置adapter和布局模式
    private void initData() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    //初始化成员
    private void initView() {
        msgList = new ArrayList<>();
        initView = view.findViewById(R.id.situation_initView);
        adapter = new MsgRecyclerViewAdapter(msgList);
        recyclerView = view.findViewById(R.id.game_situation_rv);
        gamePlayer = new GamePlayer();
        win = MediaPlayer.create(getContext(),R.raw.win);
        dead = MediaPlayer.create(getContext(),R.raw.dead);
        pin = MediaPlayer.create(getContext(),R.raw.pin);
        sharedGamePlayerData = new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(SharedGamePlayerData.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_fragment_situation1,container,false);
        return view;
    }

    //组织语言的方法
    private String text(int final_a_life, int all_dogface_hurt_to_a, int all_a_hurt_to_b, int all_a_hui_fu, String name,String rival) {
        StringBuilder builder = new StringBuilder();
        builder.append(name+"剩余生命值:"+final_a_life+"\n");
        builder.append(name+"对"+rival+"造成的总伤害:"+all_a_hurt_to_b+"\n");
        builder.append(name+"生命值回复量:"+all_a_hui_fu+"\n");
        builder.append("炮车对"+name+"造成的总伤害:"+all_dogface_hurt_to_a);
        return builder.toString();
    }

    //初始化广播接收器
    private void initBroadcastListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.wintertext.GameInActivity.finish");
        BroadcastReceiver Receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                winner = intent.getStringExtra("winner");
                final_A_life = intent.getIntExtra("final_A_life",0);
                final_B_life = intent.getIntExtra("final_B_life",0);
                all_B_hurt_to_A = intent.getIntExtra("all_B_hurt_to_A",0);
                all_A_hurt_to_B = intent.getIntExtra("all_A_hurt_to_B",0);
                all_A_hui_fu = intent.getIntExtra("all_A_hui_fu",0);
                all_B_hui_fu = intent.getIntExtra("all_B_hui_fu",0);
                all_dogface_hurt_to_A = intent.getIntExtra("all_dogface_hurt_to_A",0);
                all_dogface_hurt_to_B = intent.getIntExtra("all_dogface_hurt_to_B",0);
                get_exc = intent.getIntExtra("get_exc",0);
                get_money = intent.getIntExtra("get_money",0);
                kill_dogface = intent.getIntExtra("kill_dogface",0);

                String msg1 = text(final_A_life,all_dogface_hurt_to_A,all_A_hurt_to_B,all_A_hui_fu,"封魔剑魂","疾风剑豪");
                String msg2 = text(final_B_life,all_dogface_hurt_to_B,all_B_hurt_to_A,all_B_hui_fu,"疾风剑豪","封魔剑魂");

                String game_n = "第"+game_number+"局游戏";

                StringBuilder builder = new StringBuilder();
                builder.append(msg2);
                builder.append("\n"+"疾风剑豪补刀数:"+kill_dogface);

                String result;
                Log.d(TAG, "onReceive: "+winner+get_exc);

                if(winner.equals("疾风剑豪")) {
                    win.start();
                    result = "胜利！\n"+"获得"+get_exc+"点经验值"+"及"+get_money+"金币";
                }else if(winner.equals("封魔剑魂")){
                    dead.start();
                    result = "失败！\n"+"获得"+get_exc+"点经验值"+"及"+get_money+"金币";
                }else{
                    pin.start();
                    result = "平局！\n"+"获得"+get_exc+"点经验值"+"及"+get_money+"金币";
                }

                game_number++;
                msgList.add(new Msg(game_n,Msg.TYPE_C));
                msgList.add(new Msg(msg1,Msg.TYPE_A));
                msgList.add(new Msg(builder.toString(),Msg.TYPE_B));
                msgList.add(new Msg(result,Msg.TYPE_D));

                updateMoney();

                if(msgList.size()!=0){
                    initView.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(msgList.size()-1);
            }
        };
        getActivity().registerReceiver(Receiver,intentFilter);
    }

    //更新money
    private void updateMoney(){
        gamePlayer.setMoney(get_money);
        gamePlayer.setExc(get_exc);
        sharedGamePlayerData.setData(gamePlayer);
    }
}
