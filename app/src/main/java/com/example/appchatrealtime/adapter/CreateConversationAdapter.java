package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.CreateconversationAdapterBinding;
import com.example.appchatrealtime.viewmodels.CreateConversationViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateConversationAdapter extends RecyclerView.Adapter<CreateConversationAdapter.ViewHolder> implements Filterable {
    private ArrayList<CreateConversationViewModel> arrayList;
    private ArrayList<CreateConversationViewModel> filteredGroups;
    private Context context;



    public CreateConversationAdapter(ArrayList<CreateConversationViewModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.filteredGroups=arrayList;
        this.context = context;


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
        CreateConversationViewModel createConversationViewModel=arrayList.get(position);
        holder.bind(createConversationViewModel);

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
        public void bind(CreateConversationViewModel createConversationViewModel) {
            createconversationAdapterBinding.setViewmodel(createConversationViewModel);
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
                List<CreateConversationViewModel> fGroups = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        String data = filteredGroups.get(i).nameFull;
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
                arrayList = (ArrayList<CreateConversationViewModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
