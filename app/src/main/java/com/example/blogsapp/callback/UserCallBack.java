package com.example.blogsapp.callback;

import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onCreateUserComplete(Task<Void> task);
}
