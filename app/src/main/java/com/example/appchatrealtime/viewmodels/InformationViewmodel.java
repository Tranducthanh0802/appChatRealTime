package com.example.appchatrealtime.viewmodels;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.views.EditFragment;
import com.example.appchatrealtime.views.TopicFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InformationViewmodel extends ViewModel {
    private User user;
    private MutableLiveData<User> mutableLiveData=new MutableLiveData<>();

    public MutableLiveData<User> getMutableLiveData(FragmentActivity context) {
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String idHost=sharedPreferencesModel.getString("idHost","");

        firebase fb =new firebase();
        ArrayList<User> listUser =new ArrayList<>();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (int i = 0; i < snapshot.child("User").getChildrenCount(); i++
                ) {
                    listUser.add(snapshot.child("User").child(String.valueOf(i)).getValue(User.class));

                }
               mutableLiveData.setValue(listUser.get(Integer.parseInt(idHost)));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);

        return mutableLiveData;
    }
    public void onClickEdit(View view){
        Fragment fragment= TopicFragment.newInstance();
        if(view.getContext() instanceof AppCompatActivity) {
            FragmentTransaction transaction= ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();;
            transaction.add(R.id.frame, EditFragment.newInstance(),"Edit_frag");
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
