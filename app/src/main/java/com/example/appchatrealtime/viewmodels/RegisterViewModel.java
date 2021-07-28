package com.example.appchatrealtime.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel extends ViewModel {
    public final MutableLiveData<String> Email =new MutableLiveData<>();
    public final MutableLiveData<String> Password = new MutableLiveData<>();
    public final MutableLiveData<String> FullName = new MutableLiveData<>();
    public  MutableLiveData<String> message= new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowMessage =new MutableLiveData<>();
    public MutableLiveData<Boolean> ischeck=new MutableLiveData<>();
    private MutableLiveData<List<User>> listMutableLiveData;
    private List<User> mListUser;

    public void onCheck(){

        if(ischeck==null){
            ischeck.setValue(true);
        }
    }


    public void onClickResgister(){
        listMutableLiveData=new MutableLiveData<>();
        User user=new User(Email.getValue(),Password.getValue(),FullName.getValue());

        if(user.IsValidEmail() && user.IsValidPassword() && user.IsValidFUllName() && ischeck.getValue()){
            firebase fb=new firebase();
            mListUser=new ArrayList<>();
            DatabaseReference databaseReference= fb.getDatabaseReference("User");
            ValueEventListener postListener =new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (int i = 0; i < snapshot.getChildrenCount(); i++)
                        mListUser.add(snapshot.child(String.valueOf(i)).getValue(User.class));
                    listMutableLiveData.setValue(mListUser);
                    for (User us:mListUser) {
                        if(us.getEmail().equals(user.getEmail()) ){
                            isShowMessage.setValue(true);
                            message.setValue("Email already exist");
                            break;
                        }else {
                            isShowMessage.setValue(false);
                        }
                    }
                    Log.d("isshow", "onDataChange: "+isShowMessage.getValue());
                    if(!isShowMessage.getValue()) {
                        databaseReference.child(String.valueOf(snapshot.getChildrenCount())).setValue(user);
                        isShowMessage.setValue(true);
                        message.setValue("Register success");
                        Email.setValue("");
                        Password.setValue("");
                        FullName.setValue("");
                        ischeck.setValue(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
            databaseReference.addValueEventListener(postListener);

        }else {
            message.setValue("Login Fail");
            isShowMessage.setValue(true);
        }

    }


}
