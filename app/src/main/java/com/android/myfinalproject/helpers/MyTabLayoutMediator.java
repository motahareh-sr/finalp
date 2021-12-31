package com.android.myfinalproject.helpers;

import androidx.viewpager2.widget.ViewPager2;

import com.android.myfinalproject.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MyTabLayoutMediator {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private boolean viewPagerEffect = false;

    public MyTabLayoutMediator syncTabWithViewPager(TabLayout tabLayout, ViewPager2 viewPager2) {
        this.tabLayout = tabLayout;
        this.viewPager2 = viewPager2;
        connect();
        return this;
    }

    private void connect() {
        if (tabLayout.getTabCount() != viewPager2.getAdapter().getItemCount()) {
            for (int i = 0; i < viewPager2.getAdapter().getItemCount(); i++) {
                int res = ((ViewPagerAdapter) viewPager2.getAdapter()).getTitleRes(i);
                tabLayout.addTab(tabLayout.newTab());
//                if (res > 0)
//                    tabLayout.getTabAt(i).setText(res);
            }
        }
        tabLayout.addOnTabSelectedListener(new TabChangeListener());
        viewPager2.registerOnPageChangeCallback(new ViewPagerPageChangeListener());
    }

    private class TabChangeListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPagerEffect = true;
            viewPager2.setCurrentItem(tab.getPosition(), true);
            viewPagerEffect = false;
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    private class ViewPagerPageChangeListener extends ViewPager2.OnPageChangeCallback {

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (!viewPagerEffect)
                tabLayout.getTabAt(position).select();
        }
    }

}
