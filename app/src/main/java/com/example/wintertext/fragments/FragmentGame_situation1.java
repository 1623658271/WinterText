package com.example.wintertext.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wintertext.R;
import com.example.wintertext.adapters.MsgRecyclerViewAdapter;
import com.example.wintertext.beans.Msg;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentGame_situation1 extends Fragment {
    private List<Msg> msgList;
    private TextView initView;
    private View view;
    private RecyclerView recyclerView;
    private MsgRecyclerViewAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        msgList = new ArrayList<>();
        initView = view.findViewById(R.id.situation_initView);
        adapter = new MsgRecyclerViewAdapter(msgList);
        recyclerView = view.findViewById(R.id.game_situation_rv);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_fragment_situation1,container,false);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void getDataFromGameInActivity(){
        Bundle bundle = this.getArguments();
        if(!bundle.isEmpty()) {
            int final_A_life = bundle.getInt("final_A_life");
            int final_B_life = bundle.getInt("final_B_life");
            int all_dogface_hurt_to_A = bundle.getInt("all_dogface_hurt_to_A");
            int all_dogface_hurt_to_B = bundle.getInt("all_dogface_hurt_to_B");
            int all_A_hurt_to_B = bundle.getInt("all_A_hurt_to_B");
            int ll_B_hurt_to_A = bundle.getInt("all_B_hurt_to_A");
            int all_A_hui_fu = bundle.getInt("all_A_hui_fu");
            int all_B_hui_fu = bundle.getInt("all_B_hui_fu");
            String winner = bundle.getString("winner");
            String falser;
            String text1 = text(final_A_life,all_dogface_hurt_to_A,all_A_hurt_to_B,all_A_hui_fu,"封魂剑魔","疾风剑豪");
            String text2 = text(final_B_life,all_dogface_hurt_to_B,ll_B_hurt_to_A,all_B_hui_fu,"疾风剑豪","封魂剑魔");
            msgList.add(new Msg(text1,Msg.TYPE_A));
            msgList.add(new Msg(text2,Msg.TYPE_B));
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(msgList.size()-1);
        }
    }

    private String text(int final_a_life, int all_dogface_hurt_to_a, int all_a_hurt_to_b, int all_a_hui_fu, String name,String rival) {
        StringBuilder builder = new StringBuilder();
        builder.append(name+"剩余生命值:"+final_a_life+"\n");
        builder.append(name+"对"+rival+"造成的总伤害:"+all_a_hurt_to_b+"\n");
        builder.append(name+"生命值回复量:"+all_a_hui_fu+"\n");
        builder.append("炮车对"+name+"造成的总伤害:"+all_dogface_hurt_to_a+"\n");
        return builder.toString();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(msgList.size()!=0){
            initView.setVisibility(View.GONE);
        }
        getDataFromGameInActivity();
    }
}
