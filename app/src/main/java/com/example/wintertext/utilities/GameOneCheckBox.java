package com.example.wintertext.utilities;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

//为游戏1设置选中移动位置后的工具操作类
public class GameOneCheckBox {
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public GameOneCheckBox(List<CheckBox> checkBoxes){
        this.checkBoxes = checkBoxes;
    }

    public void allInvisible(){
        if (checkBoxes!=null){
            for(CheckBox checkBox:checkBoxes){
                checkBox.setVisibility(View.INVISIBLE);
            }
        }
    }
    public void allVisible(){
        if(checkBoxes!=null){
            for(CheckBox checkBox:checkBoxes){
                checkBox.setVisibility(View.VISIBLE);
            }
        }
    }
}
