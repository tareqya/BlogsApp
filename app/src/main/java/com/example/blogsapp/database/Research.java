package com.example.blogsapp.database;

import java.io.Serializable;
import java.util.ArrayList;

public class Research extends Uid implements Serializable {
    private String title;
    private String category;
    private String description;
    private ArrayList<Comment> comments;

    public Research(){

    }

    public String getTitle() {
        return title;
    }

    public Research setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Research setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Research setDescription(String description) {
        this.description = description;
        return this;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }
    public Research setComments(ArrayList<Comment> comments) {
        this.comments = comments;
        return this;
    }
}
