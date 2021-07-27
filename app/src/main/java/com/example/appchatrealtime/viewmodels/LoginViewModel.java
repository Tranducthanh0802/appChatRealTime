package com.example.appchatrealtime.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel   {
    public MutableLiveData<String> Email =new MutableLiveData<>();
    public   MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<String> message= new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowMessage =new MutableLiveData<>();
    private MutableLiveData<List<User>> listMutableLiveData;
    private List<User> mListUser;

//    public LoginViewModel() {
//
//    }
//    LiveData<List<User>> getUser() {
//        if (listMutableLiveData == null) {
//            listMutableLiveData = new MutableLiveData<>();
//        }
//
//        return listMutableLiveData;
//    }

    public void onClickLogin(){
//        User user=new User(Email.getValue(),Password.getValue());
//        firebase fb=new firebase();
//        mListUser=new ArrayList<>();
//        mListUser=fb.getUser("User",mListUser);
//        listMutableLiveData.setValue(mListUser);
        isShowMessage.setValue(true);
        message.setValue("Login Fail");
//        if(user.IsValidEmail() && user.IsValidPassword()){
//            for (User us:mListUser) {
//                if(us.getEmail().equals(user.getEmail()) && us.getPassword().equals(user.getPassword())){
//                    message.setValue("Login success");
//                    break;
//                }else {
//                    message.setValue("Login Fail");
//                    isShowMessage.setValue(true);
//                }
//            }
//        }else {
//            message.setValue("Login Fail");
//            isShowMessage.setValue(true);
//        }

    }
}