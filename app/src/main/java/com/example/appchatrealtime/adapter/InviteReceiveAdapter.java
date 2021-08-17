package com.example.appchatrealtime.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.ItemInviteBinding;
import com.example.appchatrealtime.model.Invite_User;
import com.example.appchatrealtime.model.ListFriend;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class InviteReceiveAdapter extends RecyclerView.Adapter<InviteReceiveAdapter.ViewHolder> {
    private ArrayList<ListFriend> arrayList;
    private ArrayList<ListFriend> filteredGroups;
    private ArrayList<Invite_User> arrInvite;
    private ArrayList<String> arrFriend;

    private Context context;



    public InviteReceiveAdapter(ArrayList<ListFriend> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;


    }

    @NonNull
    @NotNull
    @Override
    public InviteReceiveAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemInviteBinding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_invite, parent, false);
        binding.dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new InviteReceiveAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InviteReceiveAdapter.ViewHolder holder, int position) {
        ListFriend invite=arrayList.get(position);
        holder.bind(invite);
        addInvite();
        addFriend();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel((FragmentActivity) context);
        String idHost=sharedPreferencesModel.getString("idHost","");

        holder.inviteBinding.dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] arrGuest=getlistFriend(arrInvite.get(Integer.parseInt(arrayList.get(position).getId())).getInvite_send());
                String[] arrHost=getlistFriend(arrInvite.get(Integer.parseInt(idHost)).getInvite_receive());
                StringBuilder frGuest=new StringBuilder(arrFriend.get(Integer.parseInt(arrayList.get(position).getId())));
                StringBuilder frHost=new StringBuilder( arrFriend.get(Integer.parseInt(idHost)));

                String s=Remove(arrGuest,idHost);
                String r=Remove(arrHost,arrayList.get(position).getId());
                firebase fb=new firebase();
                DatabaseReference databaseReference =fb.getDatabaseReference().child("Invite");
                DatabaseReference databaseReference1 =fb.getDatabaseReference().child("Friend_User");

                if(s!=null && r!=null){
                    databaseReference.child(idHost).child("invite_receive").setValue(r+"");
                    databaseReference.child(arrayList.get(position).getId()).child("invite_send").setValue(s+"");
                    frGuest.append(idHost+",");
                    frHost.append(arrayList.get(position).getId()+",");
                    databaseReference1.child(idHost).setValue(frHost+"");
                    databaseReference1.child(arrayList.get(position).getId()).setValue(frGuest+"");

                }else {
                    databaseReference.child(idHost).child("invite_receive").setValue("");
                    databaseReference.child(arrayList.get(position).getId()).child("invite_send").setValue("");
                }
            }
        });
        holder.inviteBinding.huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] arrGuest=getlistFriend(arrInvite.get(Integer.parseInt(arrayList.get(position).getId())).getInvite_receive());
                String[] arrHost=getlistFriend(arrInvite.get(Integer.parseInt(idHost)).getInvite_send());
                String r=Remove(arrGuest,idHost);
                String s=Remove(arrHost,arrayList.get(position).getId());
                firebase fb=new firebase();
                DatabaseReference databaseReference =fb.getDatabaseReference().child("Invite");
                if(s!=null && r!=null){
                    databaseReference.child(idHost).child("invite_send").setValue(s+"");
                    databaseReference.child(arrayList.get(position).getId()).child("invite_receive").setValue(r+"");
                 }else {
                    databaseReference.child(idHost).child("invite_send").setValue("");
                    databaseReference.child(arrayList.get(position).getId()).child("invite_receive").setValue("");
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
        public ItemInviteBinding inviteBinding;
        public ViewHolder(@NonNull @NotNull ItemInviteBinding itemInviteBinding) {
            super(itemInviteBinding.getRoot());
            this.inviteBinding=itemInviteBinding;
        }
        public void bind(ListFriend invite) {
            inviteBinding.setViewmodel(invite);
            inviteBinding.executePendingBindings();

        }
        public ItemInviteBinding getAdapterBinding(){
            return inviteBinding;
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
                   String r=(String) snapshot.child(String.valueOf(i)).child("invite_receive").getValue();
                   String s=(String) snapshot.child(String.valueOf(i)).child("invite_send").getValue();
                    arrInvite.add(new Invite_User(r,s));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
    }
    public void addFriend(){
        firebase fb=new firebase();

        DatabaseReference databaseReference =fb.getDatabaseReference().child("Friend_User");
        arrFriend=new ArrayList<>();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.getChildrenCount();i++){
                       String fr=(String) snapshot.child(String.valueOf(i)).getValue();
                    arrFriend.add(fr);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
    }
    private String[] getlistFriend(String message){
        String[] arrlist = message.split(",");
        return arrlist;
    }
    private String Remove(String[] arr,String id){
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(arr));
       for(int i=0;i<list.size();i++){
           if(list.get(i).equals(id)){
               list.remove(i);
               break;
           }
       }
       arr= list.toArray(new String[list.size()]);


        return TextUtils.join(",", arr)+",";
    }
}
