package com.example.appchatrealtime;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class firebase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    public firebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

    }

    public List<User> getUser(String key,List<User> list) {
        databaseReference=firebaseDatabase.getReference().child(key);
        ValueEventListener postListener =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                   list.add(postSnapshot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
            }
        };
        databaseReference.addValueEventListener(postListener);

        return list;
    }


}
