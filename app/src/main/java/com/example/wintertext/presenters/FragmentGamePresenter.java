package com.example.wintertext.presenters;

import android.content.Context;
import android.content.Intent;

import com.example.wintertext.activities.GameInActivity;
import com.example.wintertext.utilities.ButtonChange;
import com.example.wintertext.views.IFragmentGame;

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/19 20:18
 */
public class FragmentGamePresenter<T extends IFragmentGame> implements IFragmentGamePresenter{
    private IFragmentGame iFragmentGameView;
    private Context context;
    public FragmentGamePresenter(Context context,T view){
        this.iFragmentGameView = view;
        this.context = context;
    }
    @Override
    public void enterGame(int door) {
        if(door!=0){
            iFragmentGameView.stopSelect();
            Intent intent = new Intent(context, GameInActivity.class);
            intent.putExtra("door",door);
            context.startActivity(intent);
        }else {
            iFragmentGameView.enterFalse();
        }
    }
}
