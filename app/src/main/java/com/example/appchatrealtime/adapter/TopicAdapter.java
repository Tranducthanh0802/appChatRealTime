package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.TopicAdapterBinding;
import com.example.appchatrealtime.model.ListMessage;
import com.example.appchatrealtime.respository.CustomListerner;
import com.example.appchatrealtime.viewmodels.TopicViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>  implements CustomListerner {
    private ArrayList<TopicViewModel> arrayList;
    private Context context;

    public TopicAdapter(ArrayList<TopicViewModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TopicAdapterBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.topic_adapter, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
                TopicViewModel listMessage=arrayList.get(position);
            holder.bind(listMessage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void cardClicked(ListMessage f) {
        ((AppCompatActivity) context).getSupportFragmentManager();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TopicAdapterBinding topicAdapterBinding;
        public ViewHolder(@NonNull @NotNull TopicAdapterBinding topicAdapterBinding) {
            super(topicAdapterBinding.getRoot());
            this.topicAdapterBinding=topicAdapterBinding;
        }

        public void bind(TopicViewModel topicViewModel) {
            topicAdapterBinding.setViewmodel(topicViewModel);
            topicAdapterBinding.executePendingBindings();

        }
        public TopicAdapterBinding getAdapterBinding(){
            return topicAdapterBinding;
        }
    }


}
