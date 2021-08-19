package com.example.appchatrealtime.viewmodels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Friend;

import java.util.ArrayList;

public class FriendViewModel extends ViewModel  {
    public String id;
    public String link="";
    public static Friend friend=new Friend();
    private ArrayList<FriendViewModel> arrayList=new ArrayList<>();
    private MutableLiveData<ArrayList<FriendViewModel>> arrayListMutableLiveData=new MutableLiveData<>();
    CreateConversationViewModel createConversationViewModel= new CreateConversationViewModel();
    public FriendViewModel() {
    }
    public String getLink() {
        return link;
    }

    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);

    }
    public FriendViewModel(Friend friend) {
        this.id = friend.getId();
        this.link = friend.getLink();

    }
    public MutableLiveData<ArrayList<FriendViewModel>> getArrayListMutableLiveData(){
        FriendViewModel friendViewModel=new FriendViewModel(friend);
        arrayList.add(friendViewModel);
       arrayListMutableLiveData.setValue(arrayList);
        return arrayListMutableLiveData;
    }



}
