package com.example.wintertext.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wintertext.R;

/**
 * description ： TODO:游戏规则的Dialog
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/10 13:25
 */
public class RuleDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private int layoutResId;
    private int[] listenedItem;
    public RuleDialog(Context context,int layoutResId,int[] listenedItem){
        super(context, R.style.MyDialog);
        this.context = context;
        this.layoutResId = layoutResId;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);

        for(int id:listenedItem){
            findViewById(id).setOnClickListener(this);
        }
    }

    private OnBtnListener listener;
    public interface OnBtnListener{
        void OnBtnClick(RuleDialog dialog,View view);
    }

    public void setListener(OnBtnListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        listener.OnBtnClick(this,view);
    }
}
