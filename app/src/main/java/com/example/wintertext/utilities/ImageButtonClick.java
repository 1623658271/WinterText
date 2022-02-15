package com.example.wintertext.utilities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

/**
 * description ： TODO:游戏内四个技能的一个操作类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/2 15:30
 */
//操作技能按键的工具类
public class ImageButtonClick {
    List<ImageButton> imageButtons;
    public ImageButtonClick(List<ImageButton> imageButtons){
        this.imageButtons = imageButtons;
    }
    public void StopClick(ImageButton button){
        for(ImageButton imageButton:imageButtons){
            if(imageButton==button){
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageButton,"scaleX",1f,1.5f,1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageButton,"scaleY",1f,1.5f,1f);
                animatorSet.setDuration(600);
                animatorSet.play(scaleX).with(scaleY);
                animatorSet.start();
            }else{
                imageButton.setVisibility(View.INVISIBLE);
            }
            imageButton.setEnabled(false);
        }
    }
    public void startClick(){
        for(ImageButton imageButton:imageButtons){
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setEnabled(true);
        }
    }
    public void endClick(){
        for(ImageButton imageButton:imageButtons){
            imageButton.setEnabled(false);
        }
    }
}
