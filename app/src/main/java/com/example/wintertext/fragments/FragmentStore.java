package com.example.wintertext.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;
import com.example.wintertext.beans.GamePlayer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FragmentStore extends Fragment implements View.OnClickListener {
    private View view;
    private TextView name1,name2,name3,name4,name5,name6;
    private TextView need1,need2,need3,need4,need5,need6,my;
    private int n1,n2,n3,n4,n5,n6,my_money;
    private FloatingActionButton actionButton;
    private String na1,na2,na3,na4,na5,na6;
    private String[] my_eqm = new String[100];
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6;
    private MaterialButton button1,button2,button3,button4,button5,button6;
    private List<MaterialButton> buttons = new ArrayList<>();
    private List<ImageButton> imageButtons = new ArrayList<>();
    private LinearLayout linearLayout;
    private GamePlayer gamePlayer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//初始化控件
        initImageButtonEvent();//点击事件
        initButtonEvent();//点击事件
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

    //初始化操作
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
        //获取当前金币
        my_money = Integer.parseInt(my.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store,container,false);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
