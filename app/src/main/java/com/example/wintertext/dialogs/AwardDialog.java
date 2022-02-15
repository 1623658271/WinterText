package com.example.wintertext.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.wintertext.R;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.utilities.SharedGamePlayerData;
import com.google.android.material.button.MaterialButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description ： TODO:救济金Dialog
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/15 00:52
 */
public class AwardDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private int resource;
    private int btn_get;
    private String TAG = "123";
    public AwardDialog(@NonNull Context context, int themeResId,int btn_get) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.resource = themeResId;
        this.btn_get = btn_get;
    }

    @SuppressLint({"CheckResult", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resource);
        findViewById(btn_get).setOnClickListener(this);
        MaterialButton button = findViewById(R.id.award_get);

        //判断当日是否领取
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = dateFormat.format(new Date());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(data.equals(sharedPreferences.getString("data",null))){
            button.setText("今日已领取");
        }else{
            button.setText("领取");
        }
        ImageView imageView = findViewById(R.id.award_image);
        Glide.with(context)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202005%2F30%2F20200530221552_wopkx.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1647449724&t=aacdba2441329b9b6152cfef83b27e1e")
                .placeholder(R.drawable.ic_not_get)
                .into(imageView);
    }

    private AwardDialog.OnBtnListener listener;
    public interface OnBtnListener{
        void OnBtnClick(AwardDialog dialog, View view);
    }

    public void setListener(AwardDialog.OnBtnListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.OnBtnClick(this,view);
    }
}
