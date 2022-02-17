package com.example.wintertext.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;
import com.example.wintertext.activities.MainActivity;
import com.example.wintertext.utilities.MyDatabaseHelper;

/**
 * description ： TODO:消息界面碎片
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 18:26
 */
public class FragmentMessage extends Fragment {
    private VideoView videoView;
    private SQLiteDatabase db;
    private MyDatabaseHelper dbHelper;
    private TextView textView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = view.findViewById(R.id.video_play);
        textView = view.findViewById(R.id.init_msg_message);
        dbHelper = new MyDatabaseHelper(getContext(),"Game.db",null,1);
        db = dbHelper.getWritableDatabase();
        initBroadCast();
        start_or_not();
    }

    private void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.wintertext.update_setting");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                start_or_not();
            }
        };
        getActivity().registerReceiver(receiver,intentFilter);
    }

    @SuppressLint("Range")
    private void start_or_not() {
        Cursor cursor = db.query("Game",null,"name = ?",new String[]{"yasuo"},null,null,null);
        int grade = 0;
        if(cursor.moveToFirst()){
            grade = cursor.getInt(cursor.getColumnIndex("grade"));
        }
        cursor.close();
        if(grade >= 10){
            textView.setText("奖励观看我剪的视频【狗头】,需联网");
            videoView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("https://v.kd1.qq.com/shg_321_1116_0b6bcyaayaaaluabbhwgjjqfyfqebrlqadka.f630.mp4?dis_k=73b93e29e391cbe1fdeb483281cadc93&dis_t=1644902635");
            videoView.setMediaController(new MediaController(getContext()));
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Toast.makeText(getContext(),"未知错误",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else{
            textView.setText("请升到10级再来此页面查看");
            videoView.setVisibility(View.GONE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(videoView.getVisibility()==View.GONE){
            start_or_not();
        }
    }
}
