package com.example.appchatrealtime.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appchatrealtime.views.InviteFragment;
import com.example.appchatrealtime.views.ListFriendFragment;
import com.example.appchatrealtime.views.RequestFragment;

import org.jetbrains.annotations.NotNull;

public class TabLayoutAdapter extends FragmentStateAdapter {
    public TabLayoutAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return ListFriendFragment.newInstance();
            case 1:
                return RequestFragment.newInstance();
            case 2:
                return InviteFragment.newInstance();
            default:
                return ListFriendFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
