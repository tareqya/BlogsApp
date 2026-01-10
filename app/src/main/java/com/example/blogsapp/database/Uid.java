package com.example.blogsapp.database;

import com.google.firebase.firestore.Exclude;

public class Uid {
    private String uid;

    public Uid(){}

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
