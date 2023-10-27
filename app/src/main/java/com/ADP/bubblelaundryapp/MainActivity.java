package com.ADP.bubblelaundryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = findViewById(R.id.viewPager);
        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment());
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }

    class AuthenticationPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        public AuthenticationPagerAdapter (FragmentManager fm){
            super((fm));
        }

        @Override
        public Fragment getItem(int i){
            return fragmentArrayList.get(i);
        }

        @Override
        public int getCount(){
            return fragmentArrayList.size();
        }

        void addFragment(Fragment fragment){
            fragmentArrayList.add(fragment);
        }
    }
}
