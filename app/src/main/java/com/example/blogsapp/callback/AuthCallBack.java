package com.example.blogsapp.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AuthCallBack {
    void onCreateAuthAccountComplete(Task<AuthResult> task);
    void onLoginComplete(Task<AuthResult> task);
}
