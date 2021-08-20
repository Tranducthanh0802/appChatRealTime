package com.example.appchatrealtime.viewmodels;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Friend;
import com.example.appchatrealtime.model.ItemCreateConversation;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateConversationViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ItemCreateConversation>> arrayListMutableLiveData=new MutableLiveData<>();
    private ArrayList<ItemCreateConversation> arrayList=new ArrayList<>();
    private ArrayList<Friend> arrayListChooseFriend=new ArrayList<>();
    private MutableLiveData<ArrayList<Friend>> arrayLDCF=new MutableLiveData<>();
    private MutableLiveData<Boolean> isback=new MutableLiveData<>();
    private MutableLiveData<Integer> isshow=new MutableLiveData<>();
    private String id_friend;
    firebase fb =new firebase();

    public CreateConversationViewModel() {

    }

    public CreateConversationViewModel(ArrayList<ItemCreateConversation> arrayList) {
        this.arrayList = arrayList;
    }

    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    public MutableLiveData<ArrayList<ItemCreateConversation>> getArrayListMutableLiveData(FragmentActivity context) {
        initdata(context);
        return arrayListMutableLiveData;
    }

    private MutableLiveData<ArrayList<ItemCreateConversation>> initdata(FragmentActivity context) {
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String idHost=sharedPreferencesModel.getString("idHost","");


        DatabaseReference databaseReference =fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (context == null) {
                    return;
                }
                arrayList.clear();
                ItemCreateConversation itemCreateConversation;
                ArrayList<User> arrayListU=new ArrayList<>();
                for (int i = 0; i < snapshot.child("User").getChildrenCount(); i++){
                   arrayListU.add(snapshot.child("User").child(String.valueOf(i)).getValue(User.class));
//                    if(snapshot.child("User").child(String.valueOf(i)).getValue().toString().equals(snapshot.child(idHost).getValue().toString()))
//                    {
//                        continue;
//                    }else{
//                        id_friend=String.valueOf(i);
//                        User user= snapshot.child(String.valueOf(i)).getValue(User.class);
//                        itemCreateConversation=new ItemCreateConversation(user.getFullName(),user.getLinkPhoto(),false,i);
//                        arrayList.add(itemCreateConversation);
//                    }
                }
                String[] arr=snapshot.child("Friend_User").child(idHost).getValue().toString().split(",");
                for(int i=0;i<arr.length;i++){
                    itemCreateConversation=new ItemCreateConversation(arrayListU.get(Integer.parseInt(arr[i])).getFullName(),arrayListU.get(Integer.parseInt(arr[i])).getLinkPhoto(),false,Integer.valueOf(arr[i]));
                    arrayList.add(itemCreateConversation);
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
        return arrayLDCF;
    }
    public void onClick(){
        Friend friend=new Friend();
        arrayList.get(0).getCheck();

    }

    public MutableLiveData<Boolean> getIsback() {
        return isback;
    }

    public void back(){
        if(isback.getValue()==Boolean.FALSE)
            isback.setValue(Boolean.TRUE);
        else isback.setValue(Boolean.FALSE);
    }

    public MutableLiveData<Integer> getIsshow() {
        return isshow;
    }
    public void btnCreate(){
        DatabaseReference databaseReference =fb.getDatabaseReference().child("User");
    }
}
