package com.example.blogsapp.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.blogsapp.callback.PostCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostController {
    public static final String POST_TABLE = "Posts";
    private FirebaseFirestore db;
    private PostCallBack postCallBack;


    public PostController() {
        db = FirebaseFirestore.getInstance();
    }

    public void setPostCallBack(PostCallBack postCallBack){
        this.postCallBack = postCallBack;
    }

    public void createPost(Post post){
        this.db.collection(POST_TABLE).document().set(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        postCallBack.onCreatePostComplete(task);
                    }
                });

    }

}
