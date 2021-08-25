package com.example.appchatrealtime.UI.informatinScreen;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.MainActivity;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.service.model.SharedPreferencesModel;
import com.example.appchatrealtime.service.model.User;
import com.example.appchatrealtime.service.model.firebase;
import com.example.appchatrealtime.UI.editInformationScreen.EditFragment;
import com.example.appchatrealtime.UI.bottomScreen.BottomFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InformationViewmodel extends ViewModel {
    private User user;
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getMutableLiveData(FragmentActivity context) {
        SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel(context);
        String idHost = sharedPreferencesModel.getString("idHost", "");

        firebase fb = new firebase();
        ArrayList<User> listUser = new ArrayList<>();
        DatabaseReference databaseReference = fb.getDatabaseReference();
        ValueEventListener postMessage = new ValueEventListener() {
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

    public void onClickEdit(View view) {
        Fragment fragment = BottomFragment.newInstance();
        if (view.getContext() instanceof AppCompatActivity) {
            FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
            ;
            transaction.replace(R.id.frame, EditFragment.newInstance(), "Edit_frag");

            transaction.commit();

        }

    }

    public void onClickOut(View view) {
        if (view.getContext() instanceof AppCompatActivity) {
            SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel((FragmentActivity) view.getContext());
            sharedPreferencesModel.saveString("idHost", "");
//                       FragmentTransaction transaction= ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();;
//            transaction.replace(R.id.frame, LoginFragment.newInstance(),"Login_frag");
//            transaction.commit();
            Intent i = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(i);
            ((Activity) view.getContext()).overridePendingTransition(0, 0);
        }
    }

    public void onClickSave(View view, User user) {
        firebase fb = new firebase();
        DatabaseReference databaseReference = fb.getDatabaseReference();
        SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel((FragmentActivity) view.getContext());
        String idHost = sharedPreferencesModel.getString("idHost", "");
        String password = sharedPreferencesModel.getString("password", "");
        User user1 = new User(user.getEmail(), password, user.getFullName(), user.getPhoneNumber(), user.getDate(), user.getLinkPhoto());
        if (user.IsValidFUllName()) {
            databaseReference.child("User").child(idHost).setValue(user1);
            Toast.makeText(view.getContext(), "Sua thanh cong", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBack(View view) {
        if (view.getContext() instanceof AppCompatActivity) {
            FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
            ;
            transaction.replace(R.id.frame, BottomFragment.newInstance(), "Info_frag");
            SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel((FragmentActivity) view.getContext()
            );
            transaction.commit();

        }
    }

}
