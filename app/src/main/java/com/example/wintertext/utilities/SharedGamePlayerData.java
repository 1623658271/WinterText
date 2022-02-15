package com.example.wintertext.utilities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wintertext.beans.GamePlayer;

/**
 * description ： TODO:共享Gameplayer
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/4 16:33
 */
public class SharedGamePlayerData extends ViewModel {
    private final MutableLiveData<GamePlayer> data = new MutableLiveData<GamePlayer>();

    public void setData(GamePlayer gamePlayer){
        data.setValue(gamePlayer);
    }

    public MutableLiveData<GamePlayer> getData(){
        return data;
    }
}
