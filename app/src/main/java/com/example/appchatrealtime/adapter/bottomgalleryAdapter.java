package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ItemGalleryBinding;
import com.example.appchatrealtime.viewmodels.ChatViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class bottomgalleryAdapter extends RecyclerView.Adapter<bottomgalleryAdapter.ViewHolder> {
    private ArrayList<String> arrayList;
    private Context context;

    public bottomgalleryAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public bottomgalleryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_gallery, parent, false);
        return new bottomgalleryAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ChatViewModel chatViewModel=new ChatViewModel();
        chatViewModel.setLinkPhotoGallery(arrayList.get(position));
        holder.bind(chatViewModel);
    }

    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemGalleryBinding itemGalleryBinding ;

          public ViewHolder(@NonNull @NotNull ItemGalleryBinding itemGalleryBinding) {
                super(itemGalleryBinding.getRoot());
                this.itemGalleryBinding=itemGalleryBinding;
            }

            public void bind(ChatViewModel chatViewModel) {
                itemGalleryBinding.setViewmodel(chatViewModel);
                itemGalleryBinding.executePendingBindings();

            }
            public ItemGalleryBinding getAdapterBinding(){
                return itemGalleryBinding;
            }
    }
}
