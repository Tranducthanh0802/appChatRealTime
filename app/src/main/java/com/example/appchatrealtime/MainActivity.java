package com.example.appchatrealtime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appchatrealtime.views.TopicFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, TopicFragment.newInstance()).commit();

    }
}