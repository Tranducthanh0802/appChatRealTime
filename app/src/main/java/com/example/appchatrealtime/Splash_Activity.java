package com.example.appchatrealtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appchatrealtime.model.SharedPreferencesModel;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(this);
        String idHost=sharedPreferencesModel.getString("idHost","");
        if(idHost.equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent =new Intent(Splash_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);
        }else {
            Intent intent =new Intent(Splash_Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}