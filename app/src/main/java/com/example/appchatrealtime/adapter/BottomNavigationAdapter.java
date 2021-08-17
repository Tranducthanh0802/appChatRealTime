package com.example.appchatrealtime.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appchatrealtime.views.InformationFragment;
import com.example.appchatrealtime.views.ListChatFragment;
import com.example.appchatrealtime.views.TabLayoutFragment;

import org.jetbrains.annotations.NotNull;

public class BottomNavigationAdapter extends FragmentStatePagerAdapter {
    public BottomNavigationAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListChatFragment.newInstance();
            case 1:
                return TabLayoutFragment.newInstance();
            case 2:
                return InformationFragment.newInstance();
            default:
                return ListChatFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
