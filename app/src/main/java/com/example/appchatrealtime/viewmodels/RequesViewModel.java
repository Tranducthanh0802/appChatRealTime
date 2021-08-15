package com.example.appchatrealtime.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RequesViewModel extends ViewModel {
    private String linkPhoto;
    private String nameFull;
    private Boolean isFriend;
    private ArrayList<RequesViewModel> listFriendViewModelArray=new ArrayList<>();
    private MutableLiveData<ArrayList<RequesViewModel>> listMutableLiveData=new MutableLiveData<>();


    public RequesViewModel() {
    }

    public RequesViewModel(String linkPhoto, String nameFull, Boolean isFriend) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.isFriend = isFriend;
    }

    public MutableLiveData<ArrayList<RequesViewModel>> getListMutableLiveData() {
        firebase fb=new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //String listfriend= (String) snapshot.child("Friend_User").child(String.valueOf(id_receiver)).getValue();
                //String[] arr=getlistFriend(listfriend);
                int size= (int) snapshot.child("User").getChildrenCount();
                for(int i=0;i<size;i++){
                    linkPhoto= (String) snapshot.child("User").child(String.valueOf(i)).child("linkPhoto").getValue();
                    nameFull= (String) snapshot.child("User").child(String.valueOf(i)).child("fullName").getValue();
                  //  stickHeader=nameFull;
                   // listFriendViewModelArray.add(new ListFriendViewModel(linkPhoto,nameFull,null));
                  //  arrlistName1.add(nameFull);
                }
//                for(int i=0;i<arr.length;i++){
//                    linkPhoto= (String) snapshot.child("User").child(arr[i]).child("linkPhoto").getValue();
//                    nameFull= (String) snapshot.child("User").child(arr[i]).child("fullName").getValue();
//                   ListFriendViewModel list=new ListFriendViewModel(linkPhoto,nameFull,null);
//                     listFriendViewModelArray1.get(Integer.parseInt(arr[i])).isFriend=true;
//                    Log.d("abc", "onDataChange: "+listFriendViewModelArray1.indexOf(list));
//                }
                //  listFriendViewModelArray1.get(listFriendViewModelArray1.indexOf(new ListFriendViewModel(linkPhoto,nameFull,null)));

                listMutableLiveData.setValue(listFriendViewModelArray);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData;
    }

    public String getLinkPhoto() {
        return linkPhoto;
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

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }
}
