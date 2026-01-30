package com.example.blogsapp.database;

import java.time.LocalDateTime;

public class Comment {
    private String content;
    private User author;
    private LocalDateTime timestamp;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Comment setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
