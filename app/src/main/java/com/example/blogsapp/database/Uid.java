package com.example.blogsapp.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Uid implements Serializable {
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
