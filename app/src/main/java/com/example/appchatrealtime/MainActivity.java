package com.example.appchatrealtime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.views.LoginFragment;
import com.example.appchatrealtime.views.TopicFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment=TopicFragment.newInstance();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(this);
        String idHost=sharedPreferencesModel.getString("idHost","");
        if(idHost.equals("")){
            transaction.replace(R.id.frame, LoginFragment.newInstance(),"login_frag");
        }else {
            transaction.replace(R.id.frame, TopicFragment.newInstance(),"Top_frag");
        }
        transaction.commit();

    }


}