package com.example.blogsapp.callback;

import com.example.blogsapp.database.User;
import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onCreateUserComplete(Task<Void> task);
    void onGetUserComplete(User user);
}
