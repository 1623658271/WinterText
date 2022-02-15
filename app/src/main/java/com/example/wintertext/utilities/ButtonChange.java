package com.example.wintertext.utilities;

import android.annotation.SuppressLint;

import com.example.wintertext.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * description ： TODO:游戏界面四个按钮的一个操作类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/2 9:29
 */
//一个操纵4个MaterialButton的工具类
public class ButtonChange {
    private List<MaterialButton> buttons;
    public ButtonChange(List<MaterialButton> buttons){
        this.buttons = buttons;
    }

    public void selectButton(MaterialButton button){
        for(MaterialButton materialButton:buttons){
            if(materialButton != button){
                materialButton.setChecked(false);
            }
        }
    }
    public void stopSelect(){
        for(MaterialButton materialButton:buttons){
            materialButton.setEnabled(false);
        }
    }
    public void startSelect(){
        for(MaterialButton materialButton:buttons){
            materialButton.setEnabled(true);
        }
    }
    public void cancelAll(){
        for(MaterialButton materialButton:buttons){
            materialButton.setChecked(false);
        }
    }
}
