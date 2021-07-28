package com.example.appchatrealtime.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class firebase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<User> list;

    public firebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();


    }

    public DatabaseReference getDatabaseReference(String key) {
        return databaseReference=firebaseDatabase.getReference().child("User");
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }




}
