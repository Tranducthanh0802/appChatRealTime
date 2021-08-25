package com.example.appchatrealtime.UI.listChatScreen;

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

import com.example.appchatrealtime.UI.bottomScreen.BottomFragment;
import com.example.appchatrealtime.UI.bottomScreen.BottomViewModel;
import com.example.appchatrealtime.service.model.TopicItem;
import com.example.appchatrealtime.UI.chatScreen.ChatFragment;
import com.example.appchatrealtime.UI.Base.ChooseMessageListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ListchatFragmentBinding;
import com.example.appchatrealtime.service.model.SharedPreferencesModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListChatFragment extends Fragment {
    BottomViewModel bottomViewModel;
    ListChatAdapter listChatAdapter;
    SharedPreferencesModel sharedPreferencesModel;

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
        sharedPreferencesModel = new SharedPreferencesModel(getActivity());
        bottomViewModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(BottomViewModel.class);
        binding.setLifecycleOwner(getActivity());
        bottomViewModel.getArrayListMutableLiveData(getActivity()).observe(getActivity(), new Observer<ArrayList<TopicItem>>() {
            @Override
            public void onChanged(ArrayList<TopicItem> topicViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                listChatAdapter = new ListChatAdapter(topicViewModels, getActivity());
                binding.recyclListChat.setAdapter(listChatAdapter);
                binding.recyclListChat.setLayoutManager(mLayoutManager);
                listChatAdapter.setChooseMessageListerner(new ChooseMessageListerner() {
                    @Override
                    public void id_sender(String id) {
                        Fragment fragment = BottomFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, ChatFragment.newInstance(), "Chat_frag");
                        transaction.commit();
                        sharedPreferencesModel.saveString("id_guest", id);
                    }

                    @Override
                    public void find(int count) {
                        if (count >= 1) {
                            binding.findErr.setVisibility(View.GONE);
                        } else {
                            binding.findErr.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
        bottomViewModel.getTransitionData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                listChatAdapter.getFilter().filter(s);


            }
        });

        return view;

    }


}

