package com.example.appchatrealtime.viewmodels;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Friend;
import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.respository.Choose_friendListerner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateConversationViewModel extends ViewModel {
    public String nameFull;
    public Boolean ischeck;
    public String linkPhoto;
    MutableLiveData<ArrayList<CreateConversationViewModel>> arrayListMutableLiveData=new MutableLiveData<>();
    private ArrayList<CreateConversationViewModel> arrayList=new ArrayList<>();
    private ArrayList<Friend> arrayListChooseFriend=new ArrayList<>();
    MutableLiveData<ArrayList<Friend>> arrayLDCF=new MutableLiveData<>();
    public String id_friend;
    Friend friend=new Friend();
    firebase fb =new firebase();
    private Choose_friendListerner choose_friendListerner;

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public CreateConversationViewModel() {

    }



    public void setChoose_friendListerner(Choose_friendListerner choose_friendListerner) {
        this.choose_friendListerner = choose_friendListerner;
    }

    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }



    public CreateConversationViewModel(User user, String id_friend) {
        this.nameFull = user.getFullName();
        this.ischeck = false;
        this.linkPhoto = user.getLinkPhoto();
        this.id_friend=id_friend;
    }
    public MutableLiveData<ArrayList<CreateConversationViewModel>> getArrayListMutableLiveData() {
        initdata();
        return arrayListMutableLiveData;
    }

    private MutableLiveData<ArrayList<CreateConversationViewModel>> initdata() {
        String id="1";

        DatabaseReference databaseReference =fb.getDatabaseReference().child("User");
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                CreateConversationViewModel createConversationViewModel;
                for (int i = 0; i < snapshot.getChildrenCount(); i++){
                    if(snapshot.child(String.valueOf(i)).getValue().toString().equals(snapshot.child(id).getValue().toString()))
                    {
                        continue;
                    }else{
                        id_friend=String.valueOf(i);
                        User user= snapshot.child(String.valueOf(i)).getValue(User.class);
                        createConversationViewModel=new CreateConversationViewModel(user,String.valueOf(i));
                        arrayList.add(createConversationViewModel);
                    }

                }
                arrayListMutableLiveData.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return arrayListMutableLiveData;
    }


    public MutableLiveData<ArrayList<Friend>> getArrayListMutableLiveDataListFriend(){
        arrayListChooseFriend.add(FriendViewModel.friend);
        arrayLDCF.setValue(arrayListChooseFriend);
        return arrayLDCF;
    }

    public void addFriend(CreateConversationViewModel createConversationViewModel){
        FriendViewModel friendViewModel=new FriendViewModel();
        if(ischeck){
        }else {
          choose_friendListerner.addFriend(createConversationViewModel);
        }
    }
}
