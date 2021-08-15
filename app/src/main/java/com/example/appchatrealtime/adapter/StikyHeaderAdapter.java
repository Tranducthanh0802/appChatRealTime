package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.appchatrealtime.databinding.ItemListfriendBinding;
import com.example.appchatrealtime.databinding.StickyheaderListfriendBinding;
import com.example.appchatrealtime.model.ListFriend;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class StikyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<ListFriend> arrLisName;
    private LayoutInflater mLayoutInflater;

    public StikyHeaderAdapter(List<ListFriend> arrLisName) {
        this.arrLisName = arrLisName;
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
        }
        else {
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
        ItemListfriendBinding itemBinding ;
        if (view == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) viewGroup.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            itemBinding = ItemListfriendBinding.inflate(
                    mLayoutInflater, viewGroup, false);

            view = itemBinding.getRoot();
            view.setTag(itemBinding);
        }
        else {
            itemBinding = (ItemListfriendBinding) view.getTag();
        }
        itemBinding.setViewmodel1(arrLisName.get(i));
        itemBinding.executePendingBindings();
        return view;
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
