package com.example.blogsapp.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blogsapp.R;
import com.example.blogsapp.adapter.PostAdapter;
import com.example.blogsapp.callback.PostCallBack;
import com.example.blogsapp.database.Post;
import com.example.blogsapp.database.PostController;
import com.example.blogsapp.database.User;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private TextView frag_TV_title;
    private RecyclerView frag_RV_posts;
    private User user;
    private Context context;
    private PostController postController;
    public HomeFragment(Context context) {
        this.context = context;
    }

    public void setUser(User user){
        this.user = user;
        this.displayUser();
    }

    private void displayUser() {
        frag_TV_title.setText("Hello " + user.getFirstName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        frag_TV_title = view.findViewById(R.id.frag_TV_title);
        frag_RV_posts = view.findViewById(R.id.frag_RV_posts);
    }

    private void initVars() {
        postController = new PostController();
        postController.setPostCallBack(new PostCallBack() {
            @Override
            public void onCreatePostComplete(Task<Void> task) {

            }

            @Override
            public void onFetchPostsComplete(ArrayList<Post> posts) {
                PostAdapter postAdapter = new PostAdapter(context, posts);
                frag_RV_posts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                frag_RV_posts.setHasFixedSize(true);
                frag_RV_posts.setItemAnimator(new DefaultItemAnimator());
                frag_RV_posts.setAdapter(postAdapter);
            }
        });

        postController.fetchPosts();
    }
}