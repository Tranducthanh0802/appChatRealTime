package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.ChooseMessageListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.TopicAdapter;
import com.example.appchatrealtime.databinding.ListchatFragmentBinding;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.TopicItem;
import com.example.appchatrealtime.viewmodels.TopicViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListChatFragment extends Fragment {
    TopicViewModel topicViewModel;
    TopicAdapter topicAdapter;

    public static ListChatFragment newInstance() {

        Bundle args = new Bundle();

        ListChatFragment fragment = new ListChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ListchatFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.listchat_fragment, container, false);
        View view = binding.getRoot();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(getActivity());
        topicViewModel=new ViewModelProvider(getActivity(),getDefaultViewModelProviderFactory()).get(TopicViewModel.class);
        binding.setLifecycleOwner(getActivity());
        topicViewModel.getArrayListMutableLiveData().observe(getActivity(), new Observer<ArrayList<TopicItem>>() {
            @Override
            public void onChanged(ArrayList<TopicItem> topicViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                topicAdapter=new TopicAdapter(topicViewModels,getActivity());
                binding.recyclListChat.setAdapter(topicAdapter);
                binding.recyclListChat.setLayoutManager(mLayoutManager);
                topicAdapter.setChooseMessageListerner(new ChooseMessageListerner() {
                    @Override
                    public void id_sender(String id) {
                        Fragment fragment=TopicFragment.newInstance();
                        FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.frame,ChatFragment.newInstance(),"Chat_frag");
                        transaction.addToBackStack(null);
                        transaction.commit();
                        sharedPreferencesModel.saveString("id_guest",id);
                    }
                });
            }
        });
        topicViewModel.getTransitionData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                topicAdapter.getFilter().filter(s);
            }
        });



        return view;

    }


}
