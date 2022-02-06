package com.example.wintertext.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wintertext.R;
import com.example.wintertext.adapters.FragmentPagerAdapter;
import com.example.wintertext.fragments.FragmentGame;
import com.example.wintertext.fragments.FragmentGame_game1;
import com.example.wintertext.fragments.FragmentGame_situation1;
import com.example.wintertext.fragments.FragmentMessage;
import com.example.wintertext.fragments.FragmentSetting;
import com.example.wintertext.fragments.FragmentStore;
import com.example.wintertext.utilities.MyDatabaseHelper;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.litepal.LitePal;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager2;
    private FragmentPagerAdapter adapter1;
    private RadioGroup radioGroup;
    private Toolbar toolbar;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateTable();
        setContentView(R.layout.activity_main);
        initView();//加载控件
        initAddViewPager2();//添加适配器和fragment
        initEvent();//设置各种事件
    }

    //添加适配器，fragment以及页面联动按钮
    private void initAddViewPager2() {
        radioGroup.setOnCheckedChangeListener(this);
        viewPager2.setAdapter(adapter1);
        adapter1.addFragment(new FragmentGame());
        adapter1.addFragment(new FragmentStore());
        adapter1.addFragment(new FragmentMessage());
        adapter1.addFragment(new FragmentSetting());
        viewPager2.setCurrentItem(0);

        //页面与RadioButton按钮联动
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:((RadioButton)findViewById(R.id.game)).setChecked(true);
                    break;
                    case 1:((RadioButton)findViewById(R.id.store)).setChecked(true);
                    break;
                    case 2:((RadioButton)findViewById(R.id.msg)).setChecked(true);
                    break;
                    case 3:((RadioButton)findViewById(R.id.setting)).setChecked(true);
                    break;
                }
            }
        });
    }

    //加载控件的方法
    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager2 = findViewById(R.id.main_view_pager2);
        adapter1 = new FragmentPagerAdapter(this);
        radioGroup = findViewById(R.id.radio_group_main);
        toolbar = findViewById(R.id.toolbar);
    }

    //设置各种事件的方法
    private void initEvent() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //RadioGroup按钮与页面联动
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.game:
                viewPager2.setCurrentItem(0);
                break;
            case R.id.store:
                viewPager2.setCurrentItem(1);
                break;
            case R.id.msg:viewPager2.setCurrentItem(2);
                break;
            case R.id.setting:viewPager2.setCurrentItem(3);
                break;
        }
    }

    //toolbar与menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    //toolbar中menu的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.take_photo:
                break;
            case R.id.video_watch:
                break;
            default:
                break;
        }
        return true;
    }

    //创建SQLite数据库
    private void CreateTable(){
        dbHelper = new MyDatabaseHelper(this,"Game.db",null,1);
        dbHelper.getWritableDatabase();
    }
}