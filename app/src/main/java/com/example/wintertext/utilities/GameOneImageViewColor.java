package com.example.wintertext.utilities;

import android.widget.ImageView;

import com.example.wintertext.R;
import com.example.wintertext.beans.GameOnePlayer;

import java.util.List;

public class GameOneImageViewColor {
    private List<ImageView> imageViews;
    private GameOnePlayer gameOnePlayer;
    private int player1Color = R.color.red;
    private int player2Color = R.color.blue;
    public GameOneImageViewColor(List<ImageView> imageViews,GameOnePlayer gameOnePlayer){
        this.imageViews = imageViews;
        this.gameOnePlayer = gameOnePlayer;
    }
}
