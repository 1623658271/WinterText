package com.example.wintertext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wintertext.R;
import com.example.wintertext.beans.GamePlayer;
import com.example.wintertext.fragments.FragmentGame_situation1;

public class GameInActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_in);

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
                comeByJIN();
                break;
            case R.id.location4:
                comeByJING();
                break;
            default:finish();
        }

        //传递数据给fragment_situation
        transferDataToFragment_situation();
    }

    private void comeBySI() {
    }

    private void comeBySHENG() {
    }

    private void comeByJIN() {
    }

    private void comeByJING() {
    }

    public static void gamePlay(){

    }

    //向fragment_game_situation1传递战况数据
    private void transferDataToFragment_situation() {
        FragmentGame_situation1 fragmentGame_situation1 = new FragmentGame_situation1();
        Bundle bundle = new Bundle();
        bundle.putInt("A_life",100);
        bundle.putInt("B_life",100);
        bundle.putString("winner","");
        bundle.putInt("B_kill_dog_face",0);
        bundle.putInt("B_get_money",1000);
        fragmentGame_situation1.setArguments(bundle);
    }

    public static void startGameInActivity(Context context,int i){
        Intent intent = new Intent(context,GameInActivity.class);
        intent.putExtra("door",i);
        context.startActivity(intent);
    }
}