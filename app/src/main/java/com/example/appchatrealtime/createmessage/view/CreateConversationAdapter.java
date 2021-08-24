package com.example.appchatrealtime.createmessage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.bottomnavigation.tablayout.inviteandrequest.ChooseFriendListerner;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.createmessage.model.ItemCreateConversation;
import com.example.appchatrealtime.createmessage.model.Friend;
import com.example.appchatrealtime.databinding.CreateconversationAdapterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateConversationAdapter extends RecyclerView.Adapter<CreateConversationAdapter.ViewHolder> implements Filterable {
    private ArrayList<ItemCreateConversation> arrayList;
    private ArrayList<ItemCreateConversation> filteredGroups;
    private Context context;
    private ChooseFriendListerner choose_friendListerner;

    public void setChoose_friendListerner(ChooseFriendListerner choose_friendListerner) {
        this.choose_friendListerner =  choose_friendListerner;
    }


    public CreateConversationAdapter(ArrayList<ItemCreateConversation> arrayList, Context context) {
        this.arrayList = arrayList;
        this.filteredGroups=arrayList;
        this.context = context;
        this.choose_friendListerner=choose_friendListerner;

    }

    @NonNull
    @NotNull
    @Override
    public CreateConversationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CreateconversationAdapterBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.createconversation_adapter, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ItemCreateConversation itemCreateConversation=arrayList.get(position);
        holder.bind(itemCreateConversation);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getCheck()){
                    choose_friendListerner.removeFriend(new Friend(arrayList.get(position).getLinkPhoto(),String.valueOf(arrayList.get(position).getIdFriend())));
                    arrayList.get(position).setCheck(false);
                }else {
                   arrayList.get(position).setCheck(true);
                    choose_friendListerner.addFriend(new Friend(arrayList.get(position).getLinkPhoto(),String.valueOf(arrayList.get(position).getIdFriend())));
                }
               holder.getAdapterBinding().checkbox.setChecked(arrayList.get(position).getCheck());
            }
        });
        holder.getAdapterBinding().checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getCheck()){
                    choose_friendListerner.removeFriend(new Friend(arrayList.get(position).getLinkPhoto(),String.valueOf(arrayList.get(position).getIdFriend())));
                    arrayList.get(position).setCheck(false);
                }else {
                    arrayList.get(position).setCheck(true);
                    choose_friendListerner.addFriend(new Friend(arrayList.get(position).getLinkPhoto(),String.valueOf(arrayList.get(position).getIdFriend())));
                }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CreateconversationAdapterBinding createconversationAdapterBinding;
        public ViewHolder(@NonNull @NotNull CreateconversationAdapterBinding createconversationAdapterBinding) {
            super(createconversationAdapterBinding.getRoot());
            this.createconversationAdapterBinding=createconversationAdapterBinding;
        }
        public void bind(ItemCreateConversation itemCreateConversation) {
            createconversationAdapterBinding.setViewmodel(itemCreateConversation);
            createconversationAdapterBinding.executePendingBindings();

        }
        public CreateconversationAdapterBinding getAdapterBinding(){
            return createconversationAdapterBinding;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ItemCreateConversation> fGroups = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        String data = filteredGroups.get(i).getNameFull();
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
                arrayList = (ArrayList<ItemCreateConversation>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
