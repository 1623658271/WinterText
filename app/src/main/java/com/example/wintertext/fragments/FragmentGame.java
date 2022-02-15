package com.example.wintertext.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.wintertext.R;
import com.example.wintertext.activities.MainActivity;
import com.example.wintertext.adapters.FragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * description ： TODO:游戏fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:20
 */
public class FragmentGame extends Fragment {
    private ViewPager2 viewPager2;
    private FragmentPagerAdapter adapter;
    private TabLayout tabLayout;
    private ArrayList<String> data;
    private FragmentGame_situation1 fragmentGame_situation1;
    private FragmentGame_game1 fragmentGame_game1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new FragmentPagerAdapter(this);
        viewPager2 = view.findViewById(R.id.fragment_game_viewpager2);
        tabLayout = view.findViewById(R.id.fragment_game_tab_layout);
        data = new ArrayList<>();
        data.add("对局");
        data.add("战况");
        fragmentGame_situation1 = new FragmentGame_situation1();
        fragmentGame_game1 = new FragmentGame_game1();
        adapter.addFragment(fragmentGame_game1);
        adapter.addFragment(fragmentGame_situation1);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(2);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(data.get(position));
            }
        }).attach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game,container,false);
    }
}
