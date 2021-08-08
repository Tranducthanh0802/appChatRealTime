package com.example.appchatrealtime.viewmodels;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListFriendViewModel  extends ViewModel {
    private String linkPhoto;
    private String nameFull;
    private int id_receiver=1;
    private int id_sender;
    private String stickHeader;
    private List<String> arrlistName=new ArrayList<>();
    private ArrayList<ListFriendViewModel> listFriendViewModelArray=new ArrayList<>();
    private MutableLiveData<ArrayList<ListFriendViewModel>> listMutableLiveData=new MutableLiveData<>();

    public ListFriendViewModel() {
    }

    public MutableLiveData<ArrayList<ListFriendViewModel>> getListMutableLiveData() {
        firebase fb=new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String listfriend= (String) snapshot.child("Friend_User").child(String.valueOf(id_receiver)).getValue();
                String[] arr=getlistFriend(listfriend);
                for(int i=0;i<arr.length;i++){
                    linkPhoto= (String) snapshot.child("User").child(arr[i]).child("linkPhoto").getValue();
                    nameFull= (String) snapshot.child("User").child(arr[i]).child("fullName").getValue();
                    stickHeader=nameFull;
                    listFriendViewModelArray.add(new ListFriendViewModel(linkPhoto,nameFull));
                    arrlistName.add(nameFull);
                }
                listMutableLiveData.setValue(listFriendViewModelArray);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData;
    }

    public List<String> getArrlistName() {
        return arrlistName;
    }

    public void setArrlistName(List<String> arrlistName) {
        this.arrlistName = arrlistName;
    }

    public ListFriendViewModel(String linkPhoto, String nameFull) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.stickHeader=nameFull;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }
    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }
    public String getStickHeader() {
        return stickHeader.substring(0,1);
    }

    public void setStickHeader(String stickHeader) {
        this.stickHeader = stickHeader;
    }
    private String[] getlistFriend(String message){
        String[] arrlist=message.split(",");

        return arrlist;
    }

}
