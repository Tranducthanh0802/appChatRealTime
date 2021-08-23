package com.example.appchatrealtime.views;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.ChatAdapter;
import com.example.appchatrealtime.adapter.bottomgalleryAdapter;
import com.example.appchatrealtime.databinding.MessageFragmentBinding;
import com.example.appchatrealtime.model.Chat;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.viewmodels.ChatViewModel;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private static final int STORAGE_PERMISSION_CODE = 1;
    ChatAdapter chatAdapter;
    MessageFragmentBinding binding;
    Chat chat;
    firebase fb =new firebase();
    DatabaseReference databaseReference=fb.getDatabaseReference();
    int max=0;
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
         binding = DataBindingUtil.inflate(
                inflater, R.layout.message_fragment, container, false);
        View view = binding.getRoot();
        ChatViewModel loginViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        loginViewModel.setContext(getActivity());
         chat=new Chat();
       // binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(loginViewModel);
        firebase firebase=new firebase();

        loginViewModel.getArrayListLiveData(getActivity()).observe(getActivity(), new Observer<ArrayList<ChatViewModel>>() {
            @Override
            public void onChanged(ArrayList<ChatViewModel> chatViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
               chatAdapter=new ChatAdapter(chatViewModels,getActivity());
                binding.recyclerviewmessage.setAdapter(chatAdapter);
                binding.recyclerviewmessage.setLayoutManager(mLayoutManager);
                binding.recyclerviewmessage.scrollToPosition(chatViewModels.size()-1);
                max=chatViewModels.size()-1;
                checkKeyBoard(chatViewModels.size()-1);
                binding.edtInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkKeyBoard(chatViewModels.size()-1);
                        binding.storageImg.setImageResource(R.drawable.photo);
                        binding.recBot.setVisibility(View.GONE);
                    }
                });
                binding.recyclerviewmessage.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager myLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int scrollPosition = myLayoutManager.findFirstVisibleItemPosition();
                        binding.txtCalendar.setText(chatViewModels.get(scrollPosition).getFulltime());

                    }
                });

            }
        });


         binding.storageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

                }catch (NullPointerException e){

                }
                ChatViewModel chatViewModel = new ChatViewModel();
                chatViewModel.setContext(getActivity());
                if (!chatViewModel.CheckPermission()) {
                    if (binding.recBot.getVisibility() == View.GONE) {
                        binding.storageImg.setImageResource(R.drawable.photoblue);
                        binding.recBot.setVisibility(View.VISIBLE);
                        chatViewModel.getArrayListGalleryLiveData().observe(getActivity(), new Observer<ArrayList<String>>() {
                            @Override
                            public void onChanged(ArrayList<String> strings) {
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                                bottomgalleryAdapter galleryAdapter = new bottomgalleryAdapter(strings, getActivity());
                                binding.recBot.setAdapter(galleryAdapter);
                                binding.recBot.setLayoutManager(gridLayoutManager);
                            }
                        });
                    } else {
                        binding.storageImg.setImageResource(R.drawable.photo);
                        binding.recBot.setVisibility(View.GONE);
                    }


                }
            }
        });
        loginViewModel.getCheckText().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.edtInput.setText("");
                binding.imgSend.setVisibility(View.GONE);
            }
        });

        binding.edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.recyclerviewmessage.scrollToPosition(max);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             
                if(s.length()==0){
                        binding.imgSend.setVisibility(View.GONE);
                        binding.recyclerviewmessage.scrollToPosition(max);
                    try {
                        InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

                    }catch (NullPointerException e){

                    }
                    }else  binding.imgSend.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginViewModel.getLinkPhotoLiveData(getActivity()).observe(getActivity(), new Observer<Chat>() {
            @Override
            public void onChanged(Chat s) {
                if (getActivity() == null) {
                    return;
                }
                Glide.with(getActivity()).load(s.getLinkphoto()).placeholder(R.drawable.personal1).into(binding.imgAvataTop);
                binding.namefull.setText(s.getStatus()+"");
            }
        });
        return view;
    }
    private void checkKeyBoard(int count){
        binding.messageRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r=new Rect();

                binding.messageRoot.getWindowVisibleDisplayFrame(r);
                int heightDiff=binding.messageRoot.getRootView().getHeight()-r.height();
                if(heightDiff>0.25*binding.messageRoot.getRootView().getHeight()){
                    if(count+1>0){
                        binding.recyclerviewmessage.scrollToPosition(count);
                        binding.messageRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                       }
                }
            }
        });
    }


}
