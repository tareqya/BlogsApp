package com.example.blogsapp.callback;

import com.example.blogsapp.database.Post;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface PostCallBack {
    void onCreatePostComplete(Task<Void> task);
    void onFetchPostsComplete(ArrayList<Post> posts);

}
