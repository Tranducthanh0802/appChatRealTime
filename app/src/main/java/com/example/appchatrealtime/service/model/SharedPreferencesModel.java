package com.example.appchatrealtime.service.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

public class SharedPreferencesModel {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public SharedPreferencesModel(FragmentActivity context) {
        sharedPref =  context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }
    public void saveString(String key,String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getString(String key,String value){
        return sharedPref.getString(key,"");
    }

}
