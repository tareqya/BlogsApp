package com.example.blogsapp.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blogsapp.callback.PostCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

    public void fetchPosts(){
        this.db.collection(POST_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;

                ArrayList<Post> posts = new ArrayList<>();
                for(int i = 0 ; i < value.getDocuments().size(); i++){
                    Post post = value.getDocuments().get(i).toObject(Post.class);
                    post.setUid(value.getDocuments().get(i).getId());
                    posts.add(post);
                }
                Collections.sort(posts);
                postCallBack.onFetchPostsComplete(posts);
            }
        });
    }

}
