package com.example.appchatrealtime;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appchatrealtime.databinding.ActivityMainBinding;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.views.LoginFragment;
import com.example.appchatrealtime.views.TopicFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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

    @Override
    protected void onResume() {
        super.onResume();
        boolean ret = ConnectionReceiver.isConnected();
        String msg;
        if (ret == true) {
            binding.notifiacation.setVisibility(View.GONE);
        } else {
            binding.notifiacation.setVisibility(View.VISIBLE);
        }
    }
}