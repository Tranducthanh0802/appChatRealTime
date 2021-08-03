package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

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
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>  implements CustomListerner, Filterable {
    private ArrayList<TopicViewModel> arrayList;
    private ArrayList<TopicViewModel> filteredGroups;
    private Context context;


    public TopicAdapter(ArrayList<TopicViewModel> arrayList, Context context) {
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
                TopicViewModel listMessage=arrayList.get(position);
            holder.bind(listMessage);
    }

    @Override
    public int getItemCount() {
        if(arrayList==null){
            return 0;
        }
        return arrayList.size();
    }

    @Override
    public void cardClicked(ListMessage f) {
        ((AppCompatActivity) context).getSupportFragmentManager();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<TopicViewModel> fGroups = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        String data = filteredGroups.get(i).nameSend;
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
                arrayList = (ArrayList<TopicViewModel>) results.values;
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

        public void bind(TopicViewModel topicViewModel) {
            topicAdapterBinding.setViewmodel(topicViewModel);
            topicAdapterBinding.executePendingBindings();

        }
        public TopicAdapterBinding getAdapterBinding(){
            return topicAdapterBinding;
        }
    }


}
