package com.example.wintertext.utilities;

import android.annotation.SuppressLint;

import com.example.wintertext.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;
//一个操纵4个MaterialButton的操作类
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
