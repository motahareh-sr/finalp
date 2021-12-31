package com.android.myfinalproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.myfinalproject.fragments.AboutUsFragment;
import com.android.myfinalproject.fragments.HomeFragment;
import com.android.myfinalproject.R;

public class
ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0 :
                return HomeFragment.newInstance();
            case 1 :
                return AboutUsFragment.newInstance();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public int getTitleRes(int position) {
        switch (position){
            case 0: return R.drawable.ic_baseline_ac_unit_24;
            case 1: return R.drawable.ic_baseline_person_24;
            default: return 0;
        }
    }
}
