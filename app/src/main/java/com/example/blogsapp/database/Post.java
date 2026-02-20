package com.example.blogsapp.database;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Post extends Uid implements Serializable, Comparable<Post> {
    private String title;
    private String description;
    private String image;
    private Timestamp timestamp;

    public Post(){

    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Post setImage(String imagePath) {
        this.image = imagePath;
        return this;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Post setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public int compareTo(Post post) {
        if(this.timestamp.compareTo(post.timestamp) > 0){
            return -1;
        }
        if(this.timestamp.compareTo(post.timestamp) < 0){
            return 1;
        }
        return 0;
    }
}
