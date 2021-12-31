package com.android.myfinalproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.android.myfinalproject.R;
import com.android.myfinalproject.adapters.ViewPagerAdapter;
import com.android.myfinalproject.helpers.MyTabLayoutMediator;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(R.style.Theme_MyfinalProject);
        this.setTheme(R.style.Theme_MyfinalProject);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews(){
        viewPager=findViewById(R.id.mainViewPager);
        viewPager.setAdapter(new ViewPagerAdapter(MainActivity.this));
        tabLayout=findViewById(R.id.mainTabLayout);
        tabSetup();
    }

    private void tabSetup() {

        new MyTabLayoutMediator().syncTabWithViewPager(tabLayout, viewPager);
        setTabIcons();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void setTabIcons() {
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_baseline_ac_unit_24));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_baseline_person_24));

    }
}