package com.example.appchatrealtime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ItemListfriendBinding;
import com.example.appchatrealtime.databinding.StickyheaderListfriendBinding;
import com.example.appchatrealtime.viewmodels.ListFriendViewModel;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StikyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<ListFriendViewModel> arrLisName;
    private List<String> listHeader;

    public StikyHeaderAdapter(List<ListFriendViewModel> arrLisName,List<String> listHeader) {
        this.arrLisName = arrLisName;
        this.listHeader=listHeader;
        notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            StickyheaderListfriendBinding listfriendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.stickyheader_listfriend, parent, false);

            listfriendBinding.setViewmodel(arrLisName.get(position));
            holder = new HeaderViewHolder(listfriendBinding);
            holder.view = listfriendBinding.getRoot();
            holder.view.setTag(holder);
        }
        else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        return holder.view;
    }

    @Override
    public long getHeaderId(int position) {
        return listHeader.get(position). subSequence(0, 1).charAt(0);

    }

    @Override
    public int getCount() {
       if(arrLisName.size()!=0){
           return  arrLisName.size();
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
        ItemViewHolder holder;

        if (view == null) {
            ItemListfriendBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_listfriend, viewGroup, false);
            itemBinding.setViewmodel(arrLisName.get(i));

            holder = new ItemViewHolder(itemBinding);
            holder.view = itemBinding.getRoot();
            holder.view.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) view.getTag();
        }
        return holder.view;
    }
    public class HeaderViewHolder{
        private View view;

        HeaderViewHolder(StickyheaderListfriendBinding binding)
        {
            this.view = binding.getRoot();
        }
    }
    public class ItemViewHolder{
        private View view;

        ItemViewHolder(ItemListfriendBinding binding)
        {
            this.view = binding.getRoot();
        }
    }
}
