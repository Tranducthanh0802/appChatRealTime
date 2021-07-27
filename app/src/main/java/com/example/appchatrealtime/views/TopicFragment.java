package com.example.appchatrealtime.views;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.appchatrealtime.R;

import org.jetbrains.annotations.NotNull;

import java.util.zip.Inflater;

public class TopicFragment extends Fragment {
    public static TopicFragment newInstance() {

        Bundle args = new Bundle();

        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topic_fragment, container, false);
         AHBottomNavigation bottomNavigation = (AHBottomNavigation) rootView.findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tinnhan, R.drawable.tinnhan,R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.banbe, R.drawable.people,R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.trangcanhan, R.drawable.ic_baseline_person_pin_24,R.color.white);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setAccentColor(R.color.gray);
        bottomNavigation.setInactiveColor(R.color.blueDark);
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F44336"));

// Add or remove notification for each item
        bottomNavigation.setNotification("1", 1);
        return rootView;
    }
}
