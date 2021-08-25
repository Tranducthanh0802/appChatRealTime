package com.example.appchatrealtime.UI.listFriendScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.appchatrealtime.UI.Base.ChooseMessageListerner;
import com.example.appchatrealtime.databinding.ItemListfriendBinding;
import com.example.appchatrealtime.databinding.StickyheaderListfriendBinding;
import com.example.appchatrealtime.service.model.ListFriend;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StikyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter, Filterable {
    private ArrayList<ListFriend> arrLisName;
    private ArrayList<ListFriend> filteredGroups;
    private LayoutInflater mLayoutInflater;
    ChooseMessageListerner chooseMessageListerner;

    public void setChooseMessageListerner(ChooseMessageListerner chooseMessageListerner) {
        this.chooseMessageListerner = chooseMessageListerner;
    }

    public StikyHeaderAdapter(ArrayList<ListFriend> arrLisName) {
        this.arrLisName = arrLisName;
        this.filteredGroups = arrLisName;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        StickyheaderListfriendBinding listfriendBinding;
        if (convertView == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            listfriendBinding = StickyheaderListfriendBinding.inflate(
                    mLayoutInflater, parent, false);

            convertView = listfriendBinding.getRoot();
            convertView.setTag(listfriendBinding);
        } else {
            listfriendBinding = (StickyheaderListfriendBinding) convertView.getTag();
        }
        listfriendBinding.setViewmodel1(arrLisName.get(position));
        listfriendBinding.executePendingBindings();
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return arrLisName.get(position).getStickHeader().subSequence(0, 1).charAt(0);

    }

    @Override
    public int getCount() {
        if (arrLisName.size() != 0) {
            return arrLisName.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return arrLisName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemListfriendBinding itemBinding;
        if (view == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) viewGroup.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            itemBinding = ItemListfriendBinding.inflate(
                    mLayoutInflater, viewGroup, false);

            view = itemBinding.getRoot();
            view.setTag(itemBinding);
        } else {
            itemBinding = (ItemListfriendBinding) view.getTag();
        }
        itemBinding.setViewmodel1(arrLisName.get(i));
        itemBinding.executePendingBindings();
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ListFriend> fGroups = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filteredGroups.size();
                    results.values = filteredGroups;
                } else {
                    constraint = constraint.toString().trim().toLowerCase();
                    for (int i = 0; i < filteredGroups.size(); i++) {
                        String data = filteredGroups.get(i).getNameFull();
                        if (data.toLowerCase().trim().contains(constraint.toString())) {
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
                arrLisName = (ArrayList<ListFriend>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HeaderViewHolder {
        private View view;

        HeaderViewHolder(StickyheaderListfriendBinding binding) {
            this.view = binding.getRoot();
        }
    }

    public class ItemViewHolder {
        private View view;

        ItemViewHolder(ItemListfriendBinding binding) {
            this.view = binding.getRoot();
        }
    }


}
