package com.example.appchatrealtime.bottomnavigation.tablayout.inviteandrequest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.appchatrealtime.databinding.ItemRequestfriendBinding;
import com.example.appchatrealtime.databinding.StickyheaderListfriendBinding;
import com.example.appchatrealtime.bottomnavigation.tablayout.listfriend.ListFriend;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class RequestAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<ListFriend> arrLisName;
    private LayoutInflater mLayoutInflater;
    private List<InviteUser> arrInvite;
    String invite_receive="",invite_send="",r,s;
    public RequestAdapter(List<ListFriend> arrLisName) {
        this.arrLisName = arrLisName;
        notifyDataSetChanged();
    }
    Boolean isClick;
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

        ItemRequestfriendBinding itemBinding ;
        if (view == null) {
            if (mLayoutInflater == null) {
                mLayoutInflater = (LayoutInflater) viewGroup.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
                 itemBinding = ItemRequestfriendBinding.inflate(
                    mLayoutInflater, viewGroup, false);

            view = itemBinding.getRoot();
            view.setTag(itemBinding);
        }
        else {
            itemBinding = (ItemRequestfriendBinding) view.getTag();
        }
        addInvite();
        itemBinding.setViewmodel1(arrLisName.get(i));
        isClick=true;
        itemBinding.ketban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firebase fb=new firebase();
                SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel((FragmentActivity) v.getContext());
                String idHost=sharedPreferencesModel.getString("idHost","");

                DatabaseReference databaseReference =fb.getDatabaseReference().child("Invite");
                arrInvite.get(Integer.parseInt(idHost)).getInvite_send();
                invite_receive=  arrInvite.get(i).getInvite_receive()+""+idHost+",";
                invite_send=  arrInvite.get(Integer.parseInt(idHost)).getInvite_send()+""+arrLisName.get(i).getId()+",";
                Log.d("abc", "ketban: "+invite_send+invite_receive);
                if(invite_receive.toString()!="" &&invite_receive.toString()!="" &&isClick){
                    databaseReference.child(idHost).child("invite_send").setValue(invite_send+"");
                    databaseReference.child(arrLisName.get(i).getId()).child("invite_receive").setValue(invite_receive+"");
                    isClick=false;
                }
            }
        });
        itemBinding.executePendingBindings();
        return view;
    }


    public class HeaderViewHolder{
        private StickyheaderListfriendBinding view;

        HeaderViewHolder(StickyheaderListfriendBinding binding)
        {
            this.view = binding;
        }
    }
    public class ItemViewHolder{

        private ItemRequestfriendBinding itemRequestfriendBinding;

        ItemViewHolder(ItemRequestfriendBinding binding)
        {
            this.itemRequestfriendBinding = binding;
        }
    }
    public void addInvite(){
        firebase fb=new firebase();

        DatabaseReference databaseReference =fb.getDatabaseReference().child("Invite");
        arrInvite=new ArrayList<>();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.getChildrenCount();i++){
                    r=(String) snapshot.child(String.valueOf(i)).child("invite_receive").getValue();
                    s=(String) snapshot.child(String.valueOf(i)).child("invite_send").getValue();
                    arrInvite.add(new InviteUser(r,s));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
    }


}
