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
import com.example.appchatrealtime.adapter.RequestAdapter;
import com.example.appchatrealtime.databinding.RequestFragmentBinding;
import com.example.appchatrealtime.model.ListFriend;
import com.example.appchatrealtime.viewmodels.ListFriendViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RequestFragment extends Fragment {
    public static RequestFragment newInstance() {

        Bundle args = new Bundle();

        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        RequestFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.request_fragment, container, false);
        View view = binding.getRoot();
        ListFriendViewModel listFriendViewModel=new ViewModelProvider(getActivity()).get(ListFriendViewModel.class);
        binding.setLifecycleOwner(getActivity());
        listFriendViewModel.getListMutableLiveData1(getActivity()).observe(getActivity(), new Observer<ArrayList<ListFriend>>() {
            @Override
            public void onChanged(ArrayList<ListFriend> listFriendViewModels) {
                RequestAdapter stickyListHeadersAdapter=new RequestAdapter(listFriendViewModels);
                binding.requestListFriend.setAdapter(stickyListHeadersAdapter);

            }
        });
      return view;
    }
}
