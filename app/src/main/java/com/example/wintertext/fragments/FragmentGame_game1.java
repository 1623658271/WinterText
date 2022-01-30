package com.example.wintertext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;
import com.example.wintertext.utilities.GameOneCheckBox;

import java.util.ArrayList;
import java.util.List;

public class FragmentGame_game1 extends Fragment {
    private List<CheckBox> checkBoxes;
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6;
    private GameOneCheckBox gameOneCheckBox;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_fragment_game1,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkBox1 = view.findViewById(R.id.cb_player1_main);
        checkBox2 = view.findViewById(R.id.cb_player_top);
        checkBox3 = view.findViewById(R.id.cb_player_left);
        checkBox4 = view.findViewById(R.id.cb_player_right);
        checkBox5 = view.findViewById(R.id.cb_player_bottom);
        checkBox6 = view.findViewById(R.id.cb_player2_main);

        checkBoxes = new ArrayList<>();
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
        checkBoxes.add(checkBox5);
        checkBoxes.add(checkBox6);

        gameOneCheckBox = new GameOneCheckBox(checkBoxes);
    }
}
