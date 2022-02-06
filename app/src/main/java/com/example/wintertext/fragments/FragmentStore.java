package com.example.wintertext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wintertext.R;

import java.nio.charset.StandardCharsets;

public class FragmentStore extends Fragment implements View.OnClickListener {
    private View view;
    private TextView name1,name2,name3,name4,name5,name6;
    private TextView need1,need2,need3,need4,need5,need6;
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        name1 = view.findViewById(R.id.eqm1_name);
        need1 = view.findViewById(R.id.need_money_eqm1);
        int n1 = Integer.parseInt(need1.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store,container,false);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
