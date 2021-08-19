package com.example.appchatrealtime.views;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.BottomNavigationAdapter;
import com.example.appchatrealtime.adapter.TopicAdapter;
import com.example.appchatrealtime.databinding.TopicFragmentBinding;
import com.example.appchatrealtime.model.Message;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.viewmodels.TopicViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class TopicFragment extends Fragment {
    TopicViewModel topicViewModel;
    TopicAdapter topicAdapter;
    BottomNavigationAdapter viewpager;
    int vitri;

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
        TopicFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.topic_fragment, container, false);
        View view = binding.getRoot();
         topicViewModel=new ViewModelProvider(getActivity(),getDefaultViewModelProviderFactory()).get(TopicViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setTopic(topicViewModel);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tinnhan, R.drawable.tinnhan,R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.banbe, R.drawable.people,R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.trangcanhan, R.drawable.ic_baseline_person_pin_24,R.color.white);
        binding.bottomNavigation.addItem(item1);
        binding.bottomNavigation.addItem(item2);
        binding.bottomNavigation.addItem(item3);
        binding.bottomNavigation.setAccentColor(R.color.gray);
        binding.bottomNavigation.setInactiveColor(R.color.blueDark);
        binding.bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F44336"));
// Add or remove notification for each item
        firebase fb=new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(getActivity());
        String idHost=sharedPreferencesModel.getString("idHost","");
        DatabaseReference databaseReference =fb.getDatabaseReference().child("ListMessage");
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int dem=0;
                for (int i=0;i<snapshot.getChildrenCount();i++){
                  Boolean status= snapshot.child(String.valueOf(i)).child("status").getValue(Boolean.class);
                 String s=snapshot.child(String.valueOf(i)).child("message").getValue().toString();
                  if(!status && !processMessage(s,idHost)){
                      dem++;
                  }
                }
                if(dem!=0){
                    binding.bottomNavigation.setNotification(dem+"", 0);

                }else {
                    binding.bottomNavigation.setNotification((AHNotification) null, 0);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        viewpager=new BottomNavigationAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewpager);
        binding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==2){
                    binding.viewTop.setVisibility(View.GONE);
                    Log.d("abc", "onTabSelected12: ");
                }else
                binding.viewTop.setVisibility(View.VISIBLE);
                if(position==0){
                    binding.edtSearch.setQueryHint("Tìm kiếm tin nhắn");
                }else binding.edtSearch.setQueryHint("Tìm kiếm bạn bè");

                binding.viewPager.setCurrentItem(position);
                return true;
            }
        });

        binding.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                    topicViewModel.setTransitionData(s);
                //topicAdapter.getFilter().filter(s);
                return false;
            }
        });

        binding.imgCreateMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame,CreateConversationFragment.newInstance(),"topic_frag");
                transaction.commit();
            }
        });

        return view;

    }
    private boolean processMessage(String tinnhan,String idHost) {
        if(tinnhan.equals("")){
            return true;
        }
        String[] arrSection=new String[tinnhan.split("--").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        String[] arrImage=new String[3];

        Message message=new Message();
        arrSection=tinnhan.split("@@@@@");
        arrTime=arrSection[arrSection.length-1].split("@@@@");
        arrCategory=arrTime[0].split("@@@");
        arrImage=arrCategory[0].split("@@");

        if(!arrCategory[1].equals(idHost)){
            return false;
        }else {
            return true;
        }

    }


}
