package com.example.appchatrealtime.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.TopicAdapter;
import com.example.appchatrealtime.databinding.TopicFragmentBinding;
import com.example.appchatrealtime.viewmodels.TopicViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopicFragment extends Fragment {
    TopicViewModel topicViewModel;
    TopicAdapter topicAdapter;
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
        binding.bottomNavigation.setNotification("1", 1);

        topicViewModel.getArrayListMutableLiveData().observe(getActivity(), new Observer<ArrayList<TopicViewModel>>() {
            @Override
            public void onChanged(ArrayList<TopicViewModel> topicViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                topicAdapter=new TopicAdapter(topicViewModels,getActivity());
                binding.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                       
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        topicAdapter.getFilter().filter(s);
                        return false;
                    }
                });
                binding.recyclerview.setAdapter(topicAdapter);
                binding.recyclerview.setLayoutManager(mLayoutManager);

            }
        });

        return view;

    }


}
