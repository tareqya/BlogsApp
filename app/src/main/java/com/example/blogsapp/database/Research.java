package com.example.blogsapp.database;

public class Research extends Uid{
    private String title;
    private String category;
    private String description;

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
}
