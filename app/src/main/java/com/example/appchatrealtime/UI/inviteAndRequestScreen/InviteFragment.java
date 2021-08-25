package com.example.appchatrealtime.UI.inviteAndRequestScreen;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.InviteFragmentBinding;
import com.example.appchatrealtime.service.model.ListFriend;
import com.example.appchatrealtime.UI.tabLayoutScreen.ListFriendViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InviteFragment extends Fragment {
    public static InviteFragment newInstance() {

        Bundle args = new Bundle();

        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        InviteFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.invite_fragment, container, false);
        View view = binding.getRoot();
        ListFriendViewModel loginViewModel = new ViewModelProvider(getActivity()).get(ListFriendViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(loginViewModel);
        loginViewModel.getListMutableLiveData2(getActivity()).observe(getActivity(), new Observer<ArrayList<ListFriend>>() {
            @Override
            public void onChanged(ArrayList<ListFriend> invites) {

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                InviteReceiveAdapter chatAdapter = new InviteReceiveAdapter(invites, getActivity());
                binding.recInviteReceive.setAdapter(chatAdapter);
                binding.recInviteReceive.setLayoutManager(mLayoutManager);

            }
        });
        loginViewModel.getListMutableLiveData3().observe(getActivity(), new Observer<ArrayList<ListFriend>>() {
            @Override
            public void onChanged(ArrayList<ListFriend> invites) {

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                InviteReceiveAdapter chatAdapter = new InviteReceiveAdapter(invites, getActivity());
                binding.recInviteSend.setAdapter(chatAdapter);
                binding.recInviteSend.setLayoutManager(mLayoutManager);

            }
        });
        return view;
    }
}
