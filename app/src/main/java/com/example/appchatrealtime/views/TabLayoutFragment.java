package com.example.appchatrealtime.views;

import android.graphics.Color;
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
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(getActivity());
        String idHost=sharedPreferencesModel.getString("idHost","");
//        binding.tab.getTabAt(0).getOrCreateBadge().setNumber(3);

        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator  (binding.tab, binding.viewpagerTab, new TabLayoutMediator.TabConfigurationStrategy() {
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
                        firebase fb=new firebase();
                        DatabaseReference databaseReference=fb.getDatabaseReference().child("Invite");
                        ValueEventListener postMessage=new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (getActivity() == null) {
                                    return;
                                }
                                String s= String.valueOf(snapshot.child(idHost).child("invite_receive").getValue());
                                int dem=Count(s);
                                BadgeDrawable badgeDrawable2 = tab.getOrCreateBadge();
                                if(dem>0) {
                                    badgeDrawable2.setNumber(dem);
                                    badgeDrawable2.setBackgroundColor(Color.RED);
                                    badgeDrawable2.setBadgeTextColor(Color.WHITE);
                                    badgeDrawable2.setBadgeGravity(BadgeDrawable.TOP_END);
                                    badgeDrawable2.setVisible(true);
                                }else badgeDrawable2.setVisible(false);
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        };
                        databaseReference.addValueEventListener(postMessage);
                        break;

                }
            }
        });
        tabLayoutMediator.attach();

        return view;
    }
    int Count(String s){
        if(s.equals("")) return  0;
        String[] a=s.split(",");

        return a.length;
    }
}
