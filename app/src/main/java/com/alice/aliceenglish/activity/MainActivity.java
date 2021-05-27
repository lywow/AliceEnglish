package com.alice.aliceenglish.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alice.aliceenglish.R;
import com.alice.aliceenglish.adapter.MyFragmentAdapter;
import com.alice.aliceenglish.fragment.EssayFragment;
import com.alice.aliceenglish.fragment.UserFragment;
import com.alice.aliceenglish.fragment.WordFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyFragmentAdapter myFragmentAdapter;
    private List<Fragment> fragmentList;
    private BottomNavigationView bottomNavigationView;
    private EssayFragment essayFragment;
    private WordFragment wordFragment;
    private UserFragment userFragment;

    private ImageView ivSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initFragment();
    }

    private void initData(){
    }

    private void initView(){
        ivSearch=findViewById(R.id.ivSearch);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navEssay:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navWord:
                        wordFragment.initData();
                        wordFragment.initView();
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navUser:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void initFragment(){
        fragmentList=new ArrayList<Fragment>();
        essayFragment=new EssayFragment();
        wordFragment=new WordFragment();
        userFragment=new UserFragment();

        fragmentList.add(essayFragment);
        fragmentList.add(wordFragment);
        fragmentList.add(userFragment);

        myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setAdapter(myFragmentAdapter);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //改变底部按钮颜色
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        viewPager.setCurrentItem(0);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}