package com.attitud.ssc.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.attitud.ssc.fragments.CategoryFragment;
import com.attitud.ssc.fragments.FavoriteFragment;
import com.attitud.ssc.fragments.HomeFragment;
import com.attitud.ssc.fragments.SettingFragment;
import com.attitud.ssc.fragments.TextrepeaterFragment;

public class ViewPagerAdapter  extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
            default:
                return  new HomeFragment();
            case 1:
                return new CategoryFragment();
            case 2:

                return new FavoriteFragment();
            case 3:
                return new TextrepeaterFragment();

            case 4:





            return new SettingFragment();

        }


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
