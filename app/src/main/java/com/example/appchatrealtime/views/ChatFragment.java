package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.util.Log;
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
import com.example.appchatrealtime.adapter.ChatAdapter;
import com.example.appchatrealtime.databinding.MessageFragmentBinding;
import com.example.appchatrealtime.model.Chat;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.viewmodels.ChatViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    ChatAdapter chatAdapter;
    Chat chat;
    firebase fb =new firebase();
    DatabaseReference databaseReference=fb.getDatabaseReference();
    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        MessageFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.message_fragment, container, false);
        View view = binding.getRoot();
        ChatViewModel loginViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);

        chat=new Chat();
       // binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(chat);
        FirebaseDatabase.getInstance().getReference("User")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chat.setLinkphoto(dataSnapshot.child("0").child("linkPhoto").getValue(String.class));
                        chat.setStatus(dataSnapshot.child("0").child("fullName").getValue(String.class));
                        Log.d("abc", "onDataChange: "+dataSnapshot.child("0").child("linkPhoto").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        loginViewModel.getArrayListLiveData().observe(getActivity(), new Observer<ArrayList<ChatViewModel>>() {
            @Override
            public void onChanged(ArrayList<ChatViewModel> chatViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
               chatAdapter=new ChatAdapter(chatViewModels,getActivity());
                binding.recyclerviewmessage.setAdapter(chatAdapter);
                binding.recyclerviewmessage.setLayoutManager(mLayoutManager);

                Log.d("abc", "onChanged: "+chatViewModels.get(0).getLinkPhoto());
            }
        });
        return view;
    }

}
