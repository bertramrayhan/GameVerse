package com.example.gameverse.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.gameverse.fragments.PopularGamesFragment;
import com.example.gameverse.fragments.UpcomingGamesFragment;

public class ViewPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = new PopularGamesFragment();
                break;
            case 1:
                fragment = new UpcomingGamesFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
