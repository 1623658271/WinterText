package com.example.wintertext.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * description ： TODO:适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 17:04
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {
    private ArrayList<Class> fragments;

    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if(fragments==null){
            fragments = new ArrayList<>();
        }
    }

    public FragmentPagerAdapter(Fragment fragment){
        super(fragment);
        if(fragments==null){
            fragments = new ArrayList<>();
        }
    }

    public void addFragment(Fragment fragment){
        if(fragments!=null){
            fragments.add(fragment.getClass());
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            return (Fragment) fragments.get(position).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
