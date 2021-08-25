package com.example.appchatrealtime.UI.bottomScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appchatrealtime.UI.informatinScreen.InformationFragment;
import com.example.appchatrealtime.UI.listChatScreen.ListChatFragment;
import com.example.appchatrealtime.UI.tabLayoutScreen.TabLayoutFragment;

import org.jetbrains.annotations.NotNull;

public class BottomNavigationAdapter extends FragmentStatePagerAdapter {
    public BottomNavigationAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);

    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
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
