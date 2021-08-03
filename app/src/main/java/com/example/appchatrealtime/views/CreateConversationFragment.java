package com.example.appchatrealtime.views;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.ChooseFriendAdapter;
import com.example.appchatrealtime.adapter.CreateConversationAdapter;
import com.example.appchatrealtime.databinding.CreateconversationFragmentBinding;
import com.example.appchatrealtime.respository.Choose_friendListerner;
import com.example.appchatrealtime.viewmodels.CreateConversationViewModel;
import com.example.appchatrealtime.viewmodels.FriendViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateConversationFragment extends Fragment implements Choose_friendListerner {
    CreateConversationViewModel createConversationViewModel;
    CreateConversationAdapter createConversationAdapter;
    ChooseFriendAdapter createConversationAdapterChooseFriend;
    FriendViewModel friendViewModel  =new FriendViewModel();
    public static CreateConversationFragment newInstance() {

        Bundle args = new Bundle();

        CreateConversationFragment fragment = new CreateConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        CreateconversationFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.createconversation_fragment, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(getActivity());
        createConversationViewModel=new ViewModelProvider(getActivity()).get(CreateConversationViewModel.class);
        friendViewModel=new ViewModelProvider(getActivity()).get(FriendViewModel.class);
        binding.setCreateconversation(createConversationViewModel);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerview.setLayoutManager(mLayoutManager);
        createConversationViewModel.getArrayListMutableLiveData().observe(getActivity(), new Observer<ArrayList<CreateConversationViewModel>>() {
            @Override
            public void onChanged(ArrayList<CreateConversationViewModel> createConversationViewModels) {
                createConversationAdapter =new CreateConversationAdapter(createConversationViewModels,getActivity());
                binding.recyclerview.setAdapter(createConversationAdapter);
               
                binding.edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        createConversationAdapter.getFilter().filter(s);
                        return false;
                    }
                });

           }

        });

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerview2.setLayoutManager(mLayoutManager1);

        friendViewModel.getArrayListMutableLiveData().observe(getActivity(), new Observer<ArrayList<FriendViewModel>>() {
            @Override
            public void onChanged(ArrayList<FriendViewModel> friendViewModels) {
                createConversationAdapterChooseFriend =new ChooseFriendAdapter(friendViewModels,getActivity());
                binding.recyclerview2.setAdapter(createConversationAdapterChooseFriend);

            }
        });
        return view;
    }

    @Override
    public void addFriend(CreateConversationViewModel createConversationViewModel) {
        Log.d("abc", "addFriend: "+createConversationViewModel.getLinkPhoto());
    }

    @Override
    public void removeFriend(CreateConversationViewModel createConversationViewModel) {

    }

    @Override
    public void a() {

    }
}
