package com.example.appchatrealtime.service.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase {
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;


    public DatabaseReference getDatabaseReference() {
        return databaseReference=firebaseDatabase.getReference();
    }

    public firebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    public firebase(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public DatabaseReference getDatabaseReference(String key) {
        return databaseReference=firebaseDatabase.getReference().child(key);
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }





}
