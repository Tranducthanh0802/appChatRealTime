package com.example.appchatrealtime.UI.listChatScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.service.model.TopicItem;
import com.example.appchatrealtime.UI.Base.ChooseMessageListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.TopicAdapterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> implements Filterable {
    private ArrayList<TopicItem> arrayList;
    private ArrayList<TopicItem> filteredGroups;
    private Context context;
    private ChooseMessageListerner chooseMessageListerner;

    public void setChooseMessageListerner(ChooseMessageListerner chooseMessageListerner) {
        this.chooseMessageListerner = chooseMessageListerner;
    }

    public ListChatAdapter() {
    }

    public ListChatAdapter(ArrayList<TopicItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.filteredGroups = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ListChatAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TopicAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.topic_adapter, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //arrayList.get(position).
        TopicItem listMessage = arrayList.get(position);
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
        if (arrayList == null) {
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
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        //  String data = filteredGroups.get(i).getMessages().getMessage();
                        filteredGroups.get(i).setIsShow(Boolean.TRUE);
                    }
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().trim().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        //  String data = filteredGroups.get(i).getMessages().getMessage();
                        int count = NumberSame(constraint.toString(), filteredGroups.get(i).getParagraph());
                        if (filteredGroups.get(i) != null) {
                            filteredGroups.get(i).setNotifical(count + " " + context.getString(R.string.tinnhanphuhop));
                        }
                        filteredGroups.get(i).setIsShow(Boolean.FALSE);
                        if (count > 0) {
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
                if (results.count == 0) chooseMessageListerner.find(0);
                else chooseMessageListerner.find(1);
                arrayList = (ArrayList<TopicItem>) results.values;
                notifyDataSetChanged();
            }

        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TopicAdapterBinding topicAdapterBinding;

        public ViewHolder(@NonNull @NotNull TopicAdapterBinding topicAdapterBinding) {
            super(topicAdapterBinding.getRoot());
            this.topicAdapterBinding = topicAdapterBinding;
        }

        public void bind(TopicItem topicItem) {
            topicAdapterBinding.setViewmodel1(topicItem);
            topicAdapterBinding.executePendingBindings();

        }

        public TopicAdapterBinding getAdapterBinding() {
            return topicAdapterBinding;
        }
    }

    int NumberSame(String s, String message) {
        String[] arrSection = new String[message.split("@@@@@").length + 1];
        String[] arrTime = new String[3];
        String[] arrCategory = new String[3];
        int count = 0;
        arrSection = message.split("@@@@@");
        for (int i = 0; i < message.split("@@@@@").length; i++) {
            arrTime = arrSection[i].split("@@@@");
            arrCategory = arrTime[0].split("@@@");
            if (arrCategory[0].toString().trim().toLowerCase().contains(s.trim().toLowerCase())) {
                count++;
            }
        }
        return count;
    }


}
