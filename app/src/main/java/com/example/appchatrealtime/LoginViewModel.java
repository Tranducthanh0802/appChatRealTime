package com.example.appchatrealtime;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> Email;
    public   MutableLiveData<String> Password;
    public MutableLiveData<String> message= new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowMessage =new MutableLiveData<>();
    private MutableLiveData<List<User>> listMutableLiveData;
    private List<User> mListUser;

    public LoginViewModel() {

    }
    LiveData<List<User>> getUser() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
        }

        return listMutableLiveData;
    }

    public void onClickLogin(){
        User user=new User(Email.getValue(),Password.getValue());
        firebase fb=new firebase();
        mListUser=new ArrayList<>();
        mListUser=fb.getUser("User",mListUser);
        listMutableLiveData.setValue(mListUser);
        isShowMessage.setValue(true);
        if(user.IsValidEmail() && user.IsValidPassword()){
            for (User us:mListUser) {
                if(us.getEmail().equals(user.getEmail()) && us.getPassword().equals(user.getPassword())){
                    message.setValue("Login success");
                    break;
                }else {
                    message.setValue("Login Fail");
                    isShowMessage.setValue(true);
                }
            }
        }else {
            message.setValue("Login Fail");
            isShowMessage.setValue(true);
        }

    }
}