package com.example.wintertext.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;
import com.example.wintertext.activities.GameInActivity;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.beans.Msg;
import com.example.wintertext.utilities.ButtonChange;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * description ： TODO:游戏界面的一个碎片，用于启动游戏
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:30
 */
//对游戏1碎片的相关操作
public class FragmentGame_game1 extends Fragment implements View.OnClickListener{
    private MaterialButton button1, button2, button3, button4, button_start, button_ok,button_cancel;
    private View view;
    private List<MaterialButton> buttons;
    private ButtonChange buttonChanges;
    private String winner2;
    private MediaPlayer wind;
    private String TAG = "123";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_fragment_game1, container, true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //加载操作
        initView();

        //没有点击开始时不能选取模式进入
        buttonChanges.stopSelect();

        //点击监听
        for (MaterialButton materialButton : buttons) {
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonChanges.selectButton(materialButton);
                }
            });
        }
        button_start.setOnClickListener(this);
        button_ok.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    private void initView() {
        button1 = view.findViewById(R.id.location1);
        button2 = view.findViewById(R.id.location2);
        button3 = view.findViewById(R.id.location3);
        button4 = view.findViewById(R.id.location4);
        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        button_start = view.findViewById(R.id.button_game_start);
        button_ok = view.findViewById(R.id.button_ok);
        button_cancel = view.findViewById(R.id.button_cancel);
        buttonChanges = new ButtonChange(buttons);
        wind = MediaPlayer.create(getContext(),R.raw.wind);
    }

    @Override
    public void onClick(View view) {
        //设置一个小动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(button_start, "alpha", 1f, 0f);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                button_start.setVisibility(View.GONE);
                button_ok.setVisibility(View.VISIBLE);
                button_cancel.setVisibility(View.VISIBLE);
            }
        });
        switch (view.getId()) {
            case R.id.button_game_start:
                animator.start();
                buttonChanges.startSelect();
                break;
            case R.id.button_ok:
                boolean stop = false;
                    //是否选中一门
                    for (MaterialButton materialButton : buttons) {
                        if (materialButton.isChecked()) {
                            stop = true;
                            break;
                        }
                    }
                    if(!stop){
                        Toast.makeText(getContext(), "请选择一门进入", Toast.LENGTH_SHORT).show();
                    }else{
                        buttonChanges.stopSelect();
                        button_ok.setVisibility(View.GONE);
                        button_cancel.setVisibility(View.GONE);
                        button_start.setVisibility(View.VISIBLE);
                        wind.start();
                        enterGameInActivity();
                    }
                break;
            case R.id.button_cancel:
                buttonChanges.cancelAll();
                buttonChanges.stopSelect();
                button_start.setVisibility(View.VISIBLE);
                button_start.setAlpha(1f);
                button_ok.setVisibility(View.GONE);
                button_cancel.setVisibility(View.GONE);
        }
    }

    private void enterGameInActivity() {
        int i = 0;
        for(MaterialButton materialButton:buttons){
            if(materialButton.isChecked()){
                i = materialButton.getId();
            }
        }
        Intent intent = new Intent(getContext(),GameInActivity.class);
        intent.putExtra("door",i);
        startActivityForResult(intent,1);
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonChanges.cancelAll();
        buttonChanges.stopSelect();
        button_start.setVisibility(View.VISIBLE);
        button_start.setAlpha(1f);
        button_ok.setVisibility(View.GONE);
        button_cancel.setVisibility(View.GONE);
    }
}
