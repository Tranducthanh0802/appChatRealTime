package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ItemMessageLeftBinding;
import com.example.appchatrealtime.databinding.ItemMessageRightBinding;
import com.example.appchatrealtime.viewmodels.ChatViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<ChatViewModel> arrayList;
    private Context context;
    private final int left=0;
    private final int right=1;


    public ChatAdapter(ArrayList<ChatViewModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @NotNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(viewType==0) {
            ItemMessageLeftBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_left, parent, false);
            Log.d("abc", "onDataChangeleft: ");
            return new ChatAdapter.ViewHolder(binding);
        }else {
         ItemMessageRightBinding  binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_message_right, parent, false);
            Log.d("abc", "onDataChangeright: ");
            return new ChatAdapter.ViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ChatViewModel listMessage=arrayList.get(position);
        if(!listMessage.getStatus())
        holder.bindleft(listMessage);
        else holder.bindright(listMessage);
    }

    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMessageLeftBinding leftBinding;
        public ItemMessageRightBinding rightBinding;
        public ViewHolder(@NonNull @NotNull ItemMessageRightBinding rightBinding) {
            super(rightBinding.getRoot());
            this.rightBinding=rightBinding;
        }  public ViewHolder(@NonNull @NotNull ItemMessageLeftBinding leftBinding) {
            super(leftBinding.getRoot());
            this.leftBinding=leftBinding;
        }

        public void bindleft(ChatViewModel chatViewModel) {
            leftBinding.setViewmodel(chatViewModel);
            leftBinding.executePendingBindings();

        }
        public void bindright(ChatViewModel chatViewModel) {
            rightBinding.setViewmodel(chatViewModel);
            rightBinding.executePendingBindings();

        }
        public ItemMessageLeftBinding getAdapterBindingleft(){
            return leftBinding;
        }
        public ItemMessageRightBinding getAdapterBindingright(){
            return rightBinding;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(arrayList.get(position).getStatus())
        return right;
        else
            return left;
    }
}
