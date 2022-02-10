package com.example.wintertext.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wintertext.R;
import com.example.wintertext.adapters.MsgGameInAdapter;
import com.example.wintertext.beans.Dogface;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.beans.Msg;
import com.example.wintertext.fragments.FragmentSetting;
import com.example.wintertext.utilities.ImageButtonClick;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInActivity extends AppCompatActivity {
    private MsgGameInAdapter adapter;
    private RecyclerView recyclerView;
    private List<Msg> msgList;
    private GamePlayer yong_en,ya_suo;
    private Dogface dogface;
    private int number_dogface = 1;
    private int kill_dogface = 0;
    private int exc = 0;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView yong_en_life,yasuo_life,hui_he,cao_zuo;
    private ImageButton q,w,e,r;
    private int number_hui_he = 0;
    private Random random = new Random();
    private TextView q_number_image,w_number_image;
    private LinearLayout l1,l2;
    private TextView l1_attack,l1_defense,l1_strike,l1_steal;
    private TextView l2_attack,l2_defense,l2_strike,l2_steal;
    private TextView dogface_attack;
    private MaterialButton btn_l1,btn_l2;
    private int q_number = 0;
    private int w_number = 0;
    private int A_q_number = 0;
    private int A_hurtToB, B_hurtToA, A_xi_xue, B_xi_xue, A_w_xi_xue;
    private int total_hurt_A = 0,total_hurt_B = 0,total_dogface_ToA=0,total_dogface_ToB=0;
    private int total_A_hui_fu = 0,total_B_hui_fu = 0;
    private int A_life, B_life;
    private int yasuo_e_continue = 1;
    private boolean end = false;
    private String A_jn;
    private String B_jn;
    private String A_message,B_message;
    private List<ImageButton> buttons;
    private ImageButtonClick imageButtonClick;
    private boolean A_ji_fei = false;
    private boolean B_ji_fei = false;
    private boolean happy = false;
    private boolean yasuo_have_W = false;
    private boolean strike_yong_en = false;
    private boolean strike_yasuo = false;
    private boolean SI = false,SHENG = false,BING = false,JING = false;
    private String winner = null;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_in);

        initView();//初始化
        initData();//加载数据

        //获取door数据
        Intent intent = getIntent();
        int i = intent.getIntExtra("door",0);
        switch (i){
            case R.id.location1:
                comeBySI();
                break;
            case R.id.location2:
                comeBySHENG();
                break;
            case R.id.location3:
                comeByJING();
                break;
            case R.id.location4:
                comeByBING();
                break;
            default:finish();
        }
    }

    //初始化各成员属性
    @SuppressLint("Range")
    private void initData() {
        Cursor cursor1 = db.query("Game",null,"name = ?",new String[]{"yong_en"},null,null,null);
        Cursor cursor2 = db.query("Game",null,"name = ?",new String[]{"yasuo"},null,null,null);
        Cursor cursor3 = db.query("Game",null,"name = ?",new String[]{"pao_che"},null,null,null);
        if(cursor1.moveToFirst()){
            yong_en.setName("封魔剑魂");
            yong_en.setSteal(cursor1.getInt(cursor1.getColumnIndex("steal")));
            yong_en.setStrike(cursor1.getInt(cursor1.getColumnIndex("strike")));
            yong_en.setLife(cursor1.getInt(cursor1.getColumnIndex("life")));
            yong_en.setAttack(cursor1.getInt(cursor1.getColumnIndex("attack")));
            yong_en.setDefense(cursor1.getInt(cursor1.getColumnIndex("defense")));
            yong_en.setGrade(cursor1.getInt(cursor1.getColumnIndex("grade")));
        }
        cursor1.close();
        if(cursor2.moveToFirst()){
            ya_suo.setName("疾风剑豪");
            ya_suo.setLife(cursor2.getInt(cursor2.getColumnIndex("life")));
            ya_suo.setAttack(cursor2.getInt(cursor2.getColumnIndex("attack")));
            ya_suo.setDefense(cursor2.getInt(cursor2.getColumnIndex("defense")));
            ya_suo.setStrike(cursor2.getInt(cursor2.getColumnIndex("strike")));
            ya_suo.setSteal(cursor2.getInt(cursor2.getColumnIndex("steal")));
            ya_suo.setGrade(cursor2.getInt(cursor2.getColumnIndex("grade")));
        }
        cursor2.close();
        if(cursor3.moveToFirst()){
            dogface.setGrade(cursor3.getInt(cursor3.getColumnIndex("grade")));
            dogface.setLife(cursor3.getInt(cursor3.getColumnIndex("life")));
            dogface.setName(dogface.getGrade()+"级炮车");
            dogface.setAttack(cursor3.getInt(cursor3.getColumnIndex("attack")));
            dogface.setDefense(cursor3.getInt(cursor3.getColumnIndex("defense")));
        }
        cursor3.close();
        yong_en_life.setText(String.valueOf(yong_en.getLife()));
        yasuo_life.setText(String.valueOf(ya_suo.getLife()));
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_game_in_situation);
        msgList = new ArrayList<>();
        adapter = new MsgGameInAdapter(msgList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        yong_en = new GamePlayer();
        ya_suo = new GamePlayer();
        dogface = new Dogface();
        dbHelper = new MyDatabaseHelper(this,"Game.db",null,1);
        db = dbHelper.getWritableDatabase();
        yong_en_life = findViewById(R.id.game_in_number_yongen_life);
        yasuo_life = findViewById(R.id.game_in_number_yasuo_life);
        hui_he = findViewById(R.id.tv_number_huihe);
        cao_zuo = findViewById(R.id.tv_cao_zuo);
        q_number_image = findViewById(R.id.q_number);
        w_number_image = findViewById(R.id.w_number);
        l1 = findViewById(R.id.yongen_setting);
        l2 = findViewById(R.id.personal_setting);
        l1_attack = findViewById(R.id.number_yongen_attack);
        l1_defense = findViewById(R.id.number_yongen_defense);
        l1_steal = findViewById(R.id.number_yongen_steal);
        l1_strike = findViewById(R.id.number_yongen_strike);
        l2_attack = findViewById(R.id.number_personal_attack);
        l2_defense = findViewById(R.id.number_personal_defense);
        l2_steal = findViewById(R.id.number_personal_steal);
        l2_strike = findViewById(R.id.number_personal_strike);
        dogface_attack = findViewById(R.id.dogface_attack);
        btn_l1 = findViewById(R.id.btn_rival_setting);
        btn_l2 = findViewById(R.id.btn_personal_setting);
        q = findViewById(R.id.q);
        w = findViewById(R.id.w);
        e = findViewById(R.id.e);
        r = findViewById(R.id.r);
        buttons = new ArrayList<>();
        buttons.add(q);
        buttons.add(w);
        buttons.add(e);
        buttons.add(r);
        imageButtonClick = new ImageButtonClick(buttons);
    }

    private void comeBySI() {
        SI = true;
        gamePlay(true,false,false,false);
    }

    private void comeBySHENG() {
        SHENG = true;
        gamePlay(false,true,false,false);
    }

    private void comeByBING() {
        BING = true;
        gamePlay(false,false,false,true);
    }

    private void comeByJING() {
        JING = true;
        gamePlay(false, false,true,false);
    }

    public  void gamePlay(boolean SI,boolean SHENG,boolean JING,boolean BING){
            if(SI){
                yong_en.setAttack(yong_en.getAttack()*3);
                yong_en.setDefense(yong_en.getDefense()*3);
                yong_en.setLife(yong_en.getLife()*3);
                yong_en.setStrike(yong_en.getStrike()*10);
                yong_en.setSteal(yong_en.getSteal()*2);
                yong_en_life.setText(String.valueOf(yong_en.getLife()));
            }
            if(SHENG){
                ya_suo.setLife(ya_suo.getLife()*2);
                ya_suo.setDefense(ya_suo.getDefense()*2);
                yasuo_life.setText(String.valueOf(ya_suo.getLife()));
            }
            if(BING){
                dogface.setAttack(dogface.getAttack()*5);
            }
            if(JING){

            }

            initSetting();

            A_life = yong_en.getLife();
            B_life = ya_suo.getLife();

            q.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Q_solve();
                }
            });
            w.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    W_solve();
                }
            });
            e.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    E_solve();
                }
            });
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    R_solve();
                }
            });
            btn_l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(l1.getVisibility()!=View.VISIBLE){
                        l1.setVisibility(View.VISIBLE);
                    }else{
                        l1.setVisibility(View.GONE);
                    }
                }
            });
            btn_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(l2.getVisibility()!=View.VISIBLE){
                    l2.setVisibility(View.VISIBLE);
                }else{
                    l2.setVisibility(View.GONE);
                }
            }
        });
    }

    //初始化双方查看属性的面板
    private void initSetting() {
        l1_attack.setText(String.valueOf(yong_en.getAttack()));
        l1_defense.setText(String.valueOf(yong_en.getDefense()));
        l1_strike.setText(String.valueOf(yong_en.getStrike()));
        l1_steal.setText(String.valueOf(yong_en.getSteal()));

        l2_attack.setText(String.valueOf(ya_suo.getAttack()));
        l2_defense.setText(String.valueOf(ya_suo.getDefense()));
        l2_strike.setText(String.valueOf(ya_suo.getStrike()));
        l2_steal.setText(String.valueOf(ya_suo.getSteal()));

        dogface_attack.setText(String.valueOf(dogface.getAttack()));
    }

    //判断结束游戏的方法
    private void endGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(ya_suo.getLife()==0 && yong_en.getLife()!=0){
                            end = true;
                            winner = yong_en.getName();
                            imageButtonClick.endClick();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(GameInActivity.this);
                            dialog.setMessage("你输掉了对局！")
                                    .setCancelable(false)
                                    .setNegativeButton("查看", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(GameInActivity.this,"看一下有没有bug",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setPositiveButton("焯", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).show();
                        }else if(ya_suo.getLife()!=0 && yong_en.getLife()==0){
                            end = true;
                            winner = ya_suo.getName();
                            imageButtonClick.endClick();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(GameInActivity.this);
                            dialog.setMessage("你赢得了对局！")
                                    .setCancelable(false)
                                    .setNegativeButton("查看", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(GameInActivity.this,"看一下有没有bug",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setPositiveButton("奖励自己一发", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).show();
                        }else if(ya_suo.getLife()==0 && yong_en.getLife()==0){
                            end = true;
                            winner = "平局";
                            imageButtonClick.endClick();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(GameInActivity.this);
                            dialog.setMessage("平局！")
                                    .setCancelable(false)
                                    .setNegativeButton("查看", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(GameInActivity.this,"看一下有没有bug",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setPositiveButton("居然有这种事", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).show();
                        }
                    }
                });
            }
        }).start();
    }

    //回合开始时的界面操作
    private void start(){
        A_life = yong_en.getLife();
        B_life = ya_suo.getLife();
        cao_zuo.setVisibility(View.VISIBLE);
        hui_he.setText(String.valueOf(number_hui_he));
        msgList.add(new Msg("回合"+number_hui_he,Msg.TYPE_C));
        if(yasuo_have_W) {
            w_number--;
            if (w_number <= 0) {
                yasuo_have_W = false;
                msgList.add(new Msg("\n风墙效果已结束", Msg.TYPE_C));
                w_number_image.setVisibility(View.GONE);
            } else {
                yasuo_have_W = true;
                msgList.add(new Msg("\n风墙剩余" + w_number + "回合", Msg.TYPE_C));
                w_number_image.setVisibility(View.VISIBLE);
                w_number_image.setText(String.valueOf(w_number));
            }
        }
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(msgList.size() - 1);
    }

    //显示亚索Q的层数并组织语言的方法
    private void yasuo_q_and_message(int time,int q_number_x){
        String a = B_message;
        int n = q_number_x;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                msgList.add(new Msg(a, Msg.TYPE_B));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yasuo_life.setText(String.valueOf(ya_suo.getLife()));
                        yong_en_life.setText(String.valueOf(yong_en.getLife()));
                        if(n!=0 && B_jn.equals("斩钢闪")) {
                            in_show_number(B_jn, q_number, Msg.TYPE_B);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(msgList.size() - 1);
                    }
                });
            }
        }).start();
    }

    //显示永恩Q的层数并组织语言的方法
    private void yongen_q_and_message(int time,String Ajn,int q_number_x){
        String c = A_message;
        int n = q_number_x;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                msgList.add(new Msg(c, Msg.TYPE_A));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yasuo_life.setText(String.valueOf(ya_suo.getLife()));
                        yong_en_life.setText(String.valueOf(yong_en.getLife()));
                        if(n!=0 && Ajn.equals("错玉切")) {
                            in_show_number(A_jn, A_q_number, Msg.TYPE_A);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(msgList.size() - 1);
                    }
                });
            }
        }).start();
    }

    //属性更新操作
    private void update(){
        if (A_life >= 0) {
            yong_en.setLife(A_life);
        } else {
            yong_en.setLife(0);
        }
        if (B_life >= 0) {
            ya_suo.setLife(B_life);
        } else {
            ya_suo.setLife(0);
        }
    }

    //每回合初始化暴击状态
    private void initStrike(){
        strike_yasuo = false;
        strike_yong_en = false;
    }

    //Q技能按钮的处理
    private void Q_solve(){
        initStrike();
        imageButtonClick.StopClick(q);
        B_jn = "斩钢闪";
        q_number++;
        if(q_number == 3){
            q_number = 0;
            B_ji_fei = true;
        }
        int A_hurt_all = 0;
        if(!happy){
            A_hurt_all = yong_en_all_hurt(4);
            number_hui_he++;
            start();
        }

        //二者有一个触发击飞时的方案
        if(A_ji_fei || B_ji_fei){
            //永恩触发击飞时的方案
            if(A_ji_fei && !happy){
                /*这里降低难度,因为封尘绝念斩释放一次即可击飞,不允许重复释放,防止出现一直击飞的不友好体验
                  且击飞后的操作不增加回合数，史诗级降低难度*/
                personal_yongen_jifei(A_hurt_all);
            }
            if(B_ji_fei){
                //亚索击飞时的方案
                happy=true;
                B_ji_fei=false;

                B_hurtToA = yasuo_Q_theoretical_hurt();
                B_xi_xue = life_steal(B_hurtToA,ya_suo.getSteal());

                total_hurt_B+=B_hurtToA;
                total_B_hui_fu+=B_xi_xue;

                A_life-=B_hurtToA;
                B_life+=B_xi_xue;

                update();

                B_message = text(ya_suo,B_jn,yong_en,B_xi_xue,B_hurtToA,true,strike_yasuo);

                yasuo_q_and_message(300,q_number);
            }
        }else{
            if(!happy){
                //分别计算伤害
                A_hurtToB = A_hurt_all;
                A_xi_xue = life_steal(A_hurtToB,yong_en.getSteal());

                B_hurtToA = yasuo_Q_theoretical_hurt();
                B_xi_xue = life_steal(B_hurtToA,ya_suo.getSteal());

                total_hurt_A+=A_hurtToB;
                total_A_hui_fu+=A_xi_xue;

                total_hurt_B+=B_hurtToA;
                total_B_hui_fu+=B_xi_xue;

                A_life+=A_xi_xue;
                A_life-=B_hurtToA;

                B_life+=B_xi_xue;
                B_life-=A_hurtToB;

                update();

                A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurtToB,false,strike_yong_en);
                B_message = text(ya_suo,B_jn,yong_en,B_xi_xue,B_hurtToA,false,strike_yasuo);

                yongen_q_and_message(100,A_jn,A_q_number);

            }else{
                //单独计算亚索伤害
                happy = false;
                B_hurtToA = yasuo_Q_theoretical_hurt();
                B_xi_xue = life_steal(B_hurtToA,ya_suo.getSteal());

                total_hurt_B+=B_hurtToA;
                total_B_hui_fu+=B_xi_xue;

                A_life-=B_hurtToA;
                B_life+=B_xi_xue;

                update();

                B_message = text(ya_suo,B_jn,yong_en,B_xi_xue,B_hurtToA,false,strike_yasuo);
            }
            yasuo_q_and_message(300,q_number);
        }
        if(q_number!=0){
            q_number_image.setText(String.valueOf(q_number));
            q_number_image.setVisibility(View.VISIBLE);
        }else{
            q_number_image.setVisibility(View.GONE);
        }
        if(!happy) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dogface_attack();
                                yasuo_life.setText(String.valueOf(ya_suo.getLife()));
                                yong_en_life.setText(String.valueOf(yong_en.getLife()));
                            }
                        });
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }).start();
        }
        update();
        imageButtonClick.startClick();
        endGame();
    }

    //W技能按钮的处理
    private void W_solve(){
        initStrike();
        imageButtonClick.StopClick(w);
        int a = 0;
        if(!happy){
            number_hui_he++;
            a = yong_en_all_hurt(4);
            start();
        }
        w_number=3;
        B_jn = "风之障壁";
        yasuo_have_W=true;

        if(!happy) {
            //永恩操作
            if (A_ji_fei) {
                personal_yongen_jifei(a);
            } else {
                personal_yongen(a);
            }

            //亚索操作
            yasuo_use_W(400);
            if(!happy) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dogface_attack();
                                    yasuo_life.setText(String.valueOf(ya_suo.getLife()));
                                    yong_en_life.setText(String.valueOf(yong_en.getLife()));
                                }
                            });
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }).start();
            }
        }else{
            yasuo_use_W(400);
        }
        update();
        if(yasuo_have_W){
            w_number_image.setVisibility(View.VISIBLE);
            w_number_image.setText(String.valueOf(w_number));
        }
        imageButtonClick.startClick();
        endGame();
    }

    //E技能按钮的处理
    private void E_solve(){
        initStrike();
        imageButtonClick.StopClick(e);
        int a = 0;
        B_jn = "踏前斩";
        if(!happy){
            number_hui_he++;
            a = yong_en_all_hurt(4);
            start();
        }
        if(!happy){
            //永恩操作
            if(A_ji_fei){
                personal_yongen_jifei(a);
            }else{
                personal_yongen(a);
            }

            //亚索操作
            personal_yasuo(yasuo_E_theoretical_hurt());
        }else{
            personal_yasuo(yasuo_E_theoretical_hurt());
        }

        if(!happy) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dogface_attack();
                                yasuo_life.setText(String.valueOf(ya_suo.getLife()));
                                yong_en_life.setText(String.valueOf(yong_en.getLife()));
                            }
                        });
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }).start();
        }
        update();
        if(yasuo_have_W){
            w_number_image.setVisibility(View.VISIBLE);
            w_number_image.setText(String.valueOf(w_number));
        }
        imageButtonClick.startClick();
        endGame();
    }

    //R技能按钮的处理
    private void R_solve(){
        if(!happy){
            Toast.makeText(GameInActivity.this,"无可选击飞目标！",Toast.LENGTH_SHORT).show();
        }else{
            initStrike();
            B_jn = "狂风绝息斩";
            happy = false;
            imageButtonClick.StopClick(r);
            personal_yasuo(yasuo_R_theoretical_hurt());
            update();
            if(yasuo_have_W){
                w_number_image.setVisibility(View.VISIBLE);
                w_number_image.setText(String.valueOf(w_number));
            }
            imageButtonClick.startClick();
            endGame();
        }
    }

    //亚索使用W的展示信息
    private void yasuo_use_W(int time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                    msgList.add(new Msg("面对疾风吧！\n"+ya_suo.getName()+"使用了"+B_jn+"\n开始免疫远程攻击了",Msg.TYPE_B));
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                        recyclerView.scrollToPosition(msgList.size()-1);
                    }
                });
            }
        }).start();
    }

    //回合结束展示炮车攻击的方法
    private void dogface_attack(){
        int hurtToA = dogface_hurt(yong_en.getDefense());
        A_life-=hurtToA;
        total_dogface_ToA+=hurtToA;
        msgList.add(new Msg(yong_en.getName()+"受到来自"+dogface.getName()+"的"+hurtToA+"点伤害",Msg.TYPE_C));
        if(yasuo_have_W){
            msgList.add(new Msg("亚索风墙抵挡了来自"+dogface.getName()+"的伤害",Msg.TYPE_C));
        }else {
            int hurtToB = dogface_hurt(ya_suo.getDefense());
            B_life-=hurtToB;
            total_dogface_ToB+=hurtToB;
            msgList.add(new Msg(ya_suo.getName()+"受到来自"+dogface.getName()+"的"+hurtToB+"点伤害",Msg.TYPE_C));
        }
        update();
        yasuo_life.setText(String.valueOf(B_life));
        yong_en_life.setText(String.valueOf(A_life));
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(msgList.size()-1);
    }

    //组织语言通用方法
    private String text(GamePlayer gamePlayer1,String jn,GamePlayer gamePlayer2,int xixue,int all_hurt,boolean jifei,boolean strike){
        StringBuilder builder = new StringBuilder();
        if(jn.equals("斩钢闪") && happy){
            builder.append("\n哈撒给!\n");
        }else if(jn.equals("斩钢闪")){
            int a = random.nextInt(2)+1;
            switch (a){
                case 1:builder.append("\n策！\n");
                break;
                case 2:builder.append("\n哈撒\n");
                break;
                default:
                    break;
            }
        }
        if(jn.equals("风之障壁")){
            builder.append("\n面对疾风吧！\n");
        }
        if(jn.equals("狂风绝息斩")){
            builder.append("\n索利亚给痛~！\n");
        }
        if(strike){
            builder.append("\n此次伤害为暴击！\n");
        }
        builder.append(gamePlayer1.getName() + "使用了:" + jn + "\n对" + gamePlayer2.getName() + "造成了" + all_hurt + "点伤害");
        if (xixue != 0) {
            builder.append("\n" + "并偷取了" + xixue + "点生命值");
        }
        if(jifei) {
            builder.append("\n" + gamePlayer2.getName() + "被击飞！\n" + "击飞期间:");
        }
        if(jn.equals("踏前斩")){
            builder.append("\n秀出了"+gamePlayer1.getGrade()+"级狗牌");
        }
        Log.d("123", "text: "+builder.toString());
        return builder.toString();
    }

    //永恩不击飞的操作
    private void personal_yongen(int A_hurt_all){
        A_hurtToB = A_hurt_all;
        A_xi_xue = life_steal(A_hurtToB, yong_en.getSteal());

        total_hurt_A += A_hurtToB;
        total_A_hui_fu += A_xi_xue;

        A_life += A_xi_xue;
        B_life -= A_hurtToB;

        update();

        A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurtToB,A_ji_fei,strike_yong_en);
        yongen_q_and_message(100,A_jn,A_q_number);
    }

    //亚索不击飞的操作
    private void personal_yasuo(int B_hurt_A){
        B_hurtToA = B_hurt_A;
        B_xi_xue = life_steal(B_hurtToA,ya_suo.getSteal());

        A_life-=B_hurtToA;
        B_life+=B_xi_xue;

        total_hurt_B+=B_hurtToA;
        total_B_hui_fu+=B_xi_xue;

        update();

        B_message = text(ya_suo,B_jn,yong_en,B_xi_xue,B_hurtToA,false,strike_yasuo);

        yasuo_q_and_message(400,q_number);
    }

    //永恩击飞操作
    private void personal_yongen_jifei(int A_hurt_all){
        do {
            A_ji_fei = false;
            Log.d("123", "personal_yongen_jifei: "+A_jn);
            if (A_jn.equals("错玉切")) {
                //击飞操作
                A_hurtToB = A_hurt_all;
                A_xi_xue = life_steal(A_hurtToB, yong_en.getSteal());

                total_hurt_A += A_hurtToB;
                total_A_hui_fu += A_xi_xue;

                A_life += A_xi_xue;
                B_life -= A_hurtToB;

                update();

                A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurtToB,true,strike_yong_en);
                yongen_q_and_message(100,A_jn,A_q_number);

                strike_yong_en = false;

                //击飞后的额外操作
                int A_hurt_two = yong_en_all_hurt(4);
                int A_xi_xue_two = life_steal(A_hurt_two, yong_en.getSteal());

                total_hurt_A += A_hurt_two;
                total_A_hui_fu += A_xi_xue_two;

                A_life += A_xi_xue_two;
                B_life -= A_hurt_two;

                update();

                A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurt_two,A_ji_fei,strike_yong_en);

                yongen_q_and_message(200,A_jn,A_q_number);

            } else if (A_jn.equals("封尘绝念斩")) {
                //击飞操作
                A_hurtToB = A_hurt_all;
                A_xi_xue = life_steal(A_hurtToB, yong_en.getSteal());

                total_hurt_A += A_hurtToB;
                total_A_hui_fu += A_xi_xue;

                A_life += A_xi_xue;
                B_life -= A_hurtToB;

                update();

                A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurtToB,true,strike_yong_en);
                yongen_q_and_message(100,A_jn,A_q_number);

                strike_yong_en = false;

                //击飞后的额外操作
                int A_hurt_two = yong_en_all_hurt(3);
                int A_xi_xue_two = life_steal(A_hurt_two, yong_en.getSteal());

                total_hurt_A += A_hurt_two;
                total_A_hui_fu += A_xi_xue_two;

                A_life += A_xi_xue_two;
                B_life -= A_hurt_two;

                update();
                A_message = text(yong_en,A_jn,ya_suo,A_xi_xue,A_hurt_two,A_ji_fei,strike_yong_en);
                yongen_q_and_message(200,A_jn,q_number);
            }
        }while (A_ji_fei && ya_suo.getLife()!=0 && yong_en.getLife()!=0);
    }
    //炮车对英雄造成的伤害
    private int dogface_hurt(int defense){
        return (int)((number_dogface*dogface.getAttack())*(100.0/(100.0+defense)));
    }

    //英雄对炮车造成的伤害
    private int hurtToDogface(int attack,int strike){
        return result_hurt(attack,dogface.getDefense(),isStrike(strike,dogface.getName()));
    }

    //展示Q的充能层数
    private void in_show_number(String jn,int number,int type){
        msgList.add(new Msg("\n"+jn+"获得"+number+"层充能！",type));
    }

    //一个计算最终伤害的方法
    private int result_hurt(int theoretical_hurt,int rival_defense,int number_bao_ji){
        return (int) ((theoretical_hurt * (100.0 / (100.0 + rival_defense))) * number_bao_ji);
    }

    //计算暴击倍数
    private int isStrike(int bao_ji_pro,String name){
        int r = random.nextInt(100)+1;
        if(r<=bao_ji_pro){
            if(name.equals("封魔剑魂")){
                strike_yong_en = true;
            }else if(name.equals("疾风剑豪")){
                strike_yasuo = true;
            }
            return 2;
        }else{
            if(name.equals("封魔剑魂")){
                strike_yong_en = false;
            }else if(name.equals("疾风剑豪")){
                strike_yasuo = false;
            }
            return 1;
        }
    }

    //计算生命偷取
    private int life_steal(int real_hurt,int steal){
        return (int) (real_hurt*steal*0.01);
    }

    //亚索Q总伤害
    private int yasuo_Q_theoretical_hurt(){
        return result_hurt((int)(30+1.05*ya_suo.getAttack()),yong_en.getDefense(),isStrike(ya_suo.getStrike(),ya_suo.getName()));
    }

    //亚索E总伤害
    private int yasuo_E_theoretical_hurt(){
        return result_hurt((int)(50+0.2*ya_suo.getAttack()),yong_en.getDefense(),isStrike(ya_suo.getStrike(),ya_suo.getName()));
    }

    //亚索R总伤害
    private int yasuo_R_theoretical_hurt(){
        return result_hurt((int)(200+1.5*ya_suo.getAttack()),yong_en.getDefense(),isStrike(ya_suo.getStrike(),ya_suo.getName()));
    }

    //单独计算永恩造成的总伤害
    private int yong_en_all_hurt(int n) {
        int k = random.nextInt(n)+1;
        switch (k){
            case 1:
                A_jn = "错玉切";
                if(A_q_number == 2){
                    A_q_number = 0;
                    A_ji_fei = true;
                }else{
                    A_q_number++;
                }
                return result_hurt((int)(30+1.05*yong_en.getAttack()),ya_suo.getDefense(),isStrike(yong_en.getStrike(),yong_en.getName()));
            case 2:
                A_jn = "凛神斩";
                return result_hurt((int)(5+0.1*ya_suo.getLife()),ya_suo.getDefense(),isStrike(yong_en.getStrike(),yong_en.getName()));
            case 3:
                A_jn = "破障之锋";
                return result_hurt((int)(1.35*yong_en.getAttack()),ya_suo.getDefense(),isStrike(yong_en.getStrike(),yong_en.getName()));
            case 4:
                A_jn = "封尘绝念斩";
                A_ji_fei = true;
                return result_hurt((int)(200+2.0*yong_en.getAttack()),ya_suo.getDefense(),isStrike(yong_en.getStrike(),yong_en.getName()));
            default:return 0;
        }
    }

    //向fragment_game_situation1传递战况数据
    private void transferDataToFragment_situation() {

        Intent intent = new Intent(GameInActivity.this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("final_A_life",yong_en.getLife());
        bundle.putInt("final_B_life",ya_suo.getLife());
        bundle.putInt("all_dogface_hurt_to_A",total_dogface_ToA);
        bundle.putInt("all_dogface_hurt_to_B",total_dogface_ToB);
        bundle.putInt("all_A_hurt_to_B",total_hurt_A);
        bundle.putInt("all_B_hurt_to_A",total_hurt_B);
        bundle.putInt("all_A_hui_fu",total_A_hui_fu);
        bundle.putInt("all_B_hui_fu",total_B_hui_fu);
        bundle.putString("winner",winner);

        intent.putExtras(bundle);
        setResult(1,intent);
    }

    //传递获取的经验值给fragment_setting
    private void transferDataToFragment_setting() {
        FragmentSetting fragmentSetting = new FragmentSetting();
        Bundle bundle = new Bundle();
        bundle.putInt("get_exc",exc);
        fragmentSetting.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(end){
            Toast.makeText(GameInActivity.this,"GameOver",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(GameInActivity.this, "你居然中途退出！", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void finish() {
        if(end){
            transferDataToFragment_situation();
        }
        super.finish();
    }
}