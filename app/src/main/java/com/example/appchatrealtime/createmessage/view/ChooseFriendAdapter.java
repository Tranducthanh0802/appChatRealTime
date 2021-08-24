package com.example.appchatrealtime.createmessage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.createmessage.model.Friend;
import com.example.appchatrealtime.databinding.ChooseFriendBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChooseFriendAdapter extends RecyclerView.Adapter<ChooseFriendAdapter.ViewHolder> {
    private ArrayList<Friend> arrayList;
    private Context context;
    public ChooseFriendAdapter(ArrayList<Friend> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ChooseFriendAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ChooseFriendBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.choose_friend, parent, false);
        return new ChooseFriendAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Friend friend=arrayList.get(position);
        holder.bind(friend);
    }
  
    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChooseFriendBinding chooseFriendBinding;
        public ViewHolder(@NonNull @NotNull ChooseFriendBinding chooseFriendBinding) {
            super(chooseFriendBinding.getRoot());
            this.chooseFriendBinding=chooseFriendBinding;
        }
        public void bind(Friend friend) {
            chooseFriendBinding.setViewmodel(friend);
            chooseFriendBinding.executePendingBindings();

        }
        public ChooseFriendBinding getAdapterBinding(){
            return chooseFriendBinding;
        }
    }
}
