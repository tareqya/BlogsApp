package com.example.blogsapp.database;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Comment extends Uid implements Serializable {
    private String content;
    private User author;
    private Timestamp timestamp;

    public Comment() {}

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public Comment setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Comment setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
