package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.TabLayoutAdapter;
import com.example.appchatrealtime.databinding.TablayoutFragmentBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class TabLayoutFragment  extends Fragment {
    TabLayoutAdapter adapter;
    public static TabLayoutFragment newInstance() {

        Bundle args = new Bundle();

        TabLayoutFragment fragment = new TabLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TablayoutFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.tablayout_fragment, container, false);
        View view = binding.getRoot();
        adapter=new TabLayoutAdapter(getActivity());
        binding.viewpagerTab.setAdapter(adapter);
        new TabLayoutMediator(binding.tab, binding.viewpagerTab, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Bạn bè");
                        break;
                    case 1:
                        tab.setText("Tất cả");
                        break;
                    case 2:
                        tab.setText("Yêu cầu");
                        break;

                }
            }
        }).attach();
        return view;
    }
}
