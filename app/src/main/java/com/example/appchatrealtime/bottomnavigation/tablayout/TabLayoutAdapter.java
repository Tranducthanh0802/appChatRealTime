package com.example.appchatrealtime.bottomnavigation.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appchatrealtime.bottomnavigation.tablayout.inviteandrequest.InviteFragment;
import com.example.appchatrealtime.bottomnavigation.tablayout.listfriend.ListFriendFragment;
import com.example.appchatrealtime.bottomnavigation.tablayout.allpeople.AllpeopleFragment;

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
                return AllpeopleFragment.newInstance();
            default:
                return InviteFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
