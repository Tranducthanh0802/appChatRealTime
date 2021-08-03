package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ChooseFriendBinding;
import com.example.appchatrealtime.viewmodels.FriendViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChooseFriendAdapter extends RecyclerView.Adapter<ChooseFriendAdapter.ViewHolder> {
    private ArrayList<FriendViewModel> arrayList;
    private Context context;
    public ChooseFriendAdapter(ArrayList<FriendViewModel> arrayList, Context context) {
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
        FriendViewModel friend=arrayList.get(position);
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
        public void bind(FriendViewModel friendViewModel) {
            chooseFriendBinding.setFviewmodel(friendViewModel);
            chooseFriendBinding.executePendingBindings();

        }
        public ChooseFriendBinding getAdapterBinding(){
            return chooseFriendBinding;
        }
    }
}
