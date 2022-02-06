package com.example.wintertext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;

public class FragmentGame_situation1 extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_fragment_situation1,container,false);
    }

    public void getDataFromGameInActivity(){
        Bundle bundle = FragmentGame_situation1.this.getArguments();
        bundle.getInt("A_life");
        bundle.getInt("B_life");
        bundle.getInt("B_kill_dog_face");
        bundle.getInt("B_get_money");
    }
}
