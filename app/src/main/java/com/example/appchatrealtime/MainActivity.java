package com.example.appchatrealtime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appchatrealtime.service.responsive.connectservice.ConnectionReceiver;
import com.example.appchatrealtime.databinding.ActivityMainBinding;
import com.example.appchatrealtime.service.model.SharedPreferencesModel;
import com.example.appchatrealtime.service.model.firebase;
import com.example.appchatrealtime.UI.loginScreen.LoginFragment;
import com.example.appchatrealtime.UI.bottomScreen.BottomFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private long backPressTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Fragment fragment = BottomFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel(this);
        String idHost = sharedPreferencesModel.getString("idHost", "");
        if (idHost.equals("")) {
            transaction.replace(R.id.frame, LoginFragment.newInstance(), "login_frag");
        } else {
            transaction.replace(R.id.frame, BottomFragment.newInstance(), "Top_frag");
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

    Boolean isClick;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1000) {
            firebase fb = new firebase();
            DatabaseReference databaseReference = fb.getDatabaseReference();
            SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel(this);
            StorageReference root = FirebaseStorage.getInstance().getReference();
            StorageReference fileref = root.child("Image/" + System.currentTimeMillis());
            isClick = true;
            String idHost = sharedPreferencesModel.getString("idHost", "");
            Uri file = data.getData();
            fileref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("abc", "onActivityResult: " + uri);
                            if (isClick) {
                                databaseReference.child("User").child(idHost).child("linkPhoto").setValue(uri.toString() + "");
                                isClick = false;
                            }
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onBackPressed() {

        if (backPressTimes + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, this.getString(R.string.bamthemlannua) + "", Toast.LENGTH_SHORT).show();
        }
        backPressTimes = System.currentTimeMillis();
    }
}