package com.example.blogsapp.callback;

import com.google.android.gms.tasks.Task;

public interface PostCallBack {
    void onCreatePostComplete(Task<Void> task);

}
