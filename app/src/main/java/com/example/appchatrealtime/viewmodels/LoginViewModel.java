package com.example.appchatrealtime.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

public class LoginViewModel extends ViewModel   {
    public final MutableLiveData<String> Email =new MutableLiveData<>();
    public final MutableLiveData<String> Password = new MutableLiveData<>();
    public  MutableLiveData<String> message= new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowMessage =new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowNotifica =new MutableLiveData<>();

    private MutableLiveData<List<User>> listMutableLiveData;
    private List<User> mListUser;

//    public LoginViewModel() {
//
//    }
    LiveData<List<User>> getUser() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }

        return listMutableLiveData;
    }

    public void onClickLogin(){
        listMutableLiveData=new MutableLiveData<>();
        User user=new User(Email.getValue(),Password.getValue());
        if(user.IsValidEmail() && user.IsValidPassword() ){
            firebase fb=new firebase();
            mListUser=new ArrayList<>();
            DatabaseReference databaseReference= fb.getDatabaseReference("User");
            ValueEventListener postListener =new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mListUser.clear();
                    for (int i = 0; i < snapshot.getChildrenCount(); i++)
                        mListUser.add(snapshot.child(String.valueOf(i)).getValue(User.class));
                    listMutableLiveData.setValue(mListUser);
                    for (User us:mListUser) {
                        if(us.getEmail().equals(user.getEmail()) && us.getPassword().equals(user.getPassword())){
                           isShowMessage.setValue(false);
                            break;
                        }else {
                            message.setValue("Login Fail");
                            isShowMessage.setValue(true);
                        }
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
    public void onclickDK(){
        isShowNotifica.setValue(true);

    }
}