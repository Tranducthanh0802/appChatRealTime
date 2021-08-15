package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.StikyHeaderAdapter;
import com.example.appchatrealtime.databinding.ListFriendFragmentBinding;
import com.example.appchatrealtime.model.ListFriend;
import com.example.appchatrealtime.viewmodels.ListFriendViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ListFriendFragment extends Fragment {
    public static ListFriendFragment newInstance() {

        Bundle args = new Bundle();

        ListFriendFragment fragment = new ListFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ListFriendFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.list_friend_fragment, container, false);
        View view = binding.getRoot();
        ListFriendViewModel listFriendViewModel=new ViewModelProvider(getActivity()).get(ListFriendViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(listFriendViewModel);
        listFriendViewModel.getListMutableLiveData().observe(getActivity(), new Observer<ArrayList<ListFriend>>() {
            @Override
            public void onChanged(ArrayList<ListFriend> listFriendViewModels) {
                StickyListHeadersAdapter stickyListHeadersAdapter=new StikyHeaderAdapter(listFriendViewModels);
                binding.stickyListFriend.setAdapter(stickyListHeadersAdapter);
            }
        });
        return view;
    }
}