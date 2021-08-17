package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.ChooseMessageListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.TopicAdapterBinding;
import com.example.appchatrealtime.model.TopicItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>  implements  Filterable {
    private ArrayList<TopicItem> arrayList;
    private ArrayList<TopicItem> filteredGroups;
    private Context context;
    private ChooseMessageListerner chooseMessageListerner;

    public void setChooseMessageListerner(ChooseMessageListerner chooseMessageListerner) {
        this.chooseMessageListerner = chooseMessageListerner;
    }

    public TopicAdapter() {
    }

    public TopicAdapter(ArrayList<TopicItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.filteredGroups=arrayList;
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
        TopicItem listMessage=arrayList.get(position);
        holder.bind(listMessage);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               chooseMessageListerner.id_sender(arrayList.get(position).getIdGuest());
           }
       });
    }

    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<TopicItem> fGroups = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        String data = filteredGroups.get(i).getMessages().getMessage();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            fGroups.add(filteredGroups.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = fGroups.size();
                    results.values = fGroups;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<TopicItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TopicAdapterBinding topicAdapterBinding;
        public ViewHolder(@NonNull @NotNull TopicAdapterBinding topicAdapterBinding) {
            super(topicAdapterBinding.getRoot());
            this.topicAdapterBinding=topicAdapterBinding;
        }

        public void bind(TopicItem topicItem) {
            topicAdapterBinding.setViewmodel1(topicItem);
            topicAdapterBinding.executePendingBindings();

        }
        public TopicAdapterBinding getAdapterBinding(){
            return topicAdapterBinding;
        }
    }


}
