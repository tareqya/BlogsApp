package com.example.blogsapp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blogsapp.R;
import com.example.blogsapp.auth.LoginActivity;
import com.example.blogsapp.database.User;
import com.example.blogsapp.database.UserController;


public class ProfileFragment extends Fragment {

    public static final String USER_KEY = "USER";
    private TextView fragProfile_TV_name;
    private TextView fragProfile_TV_email;
    private CardView fragProfile_CV_editDetails;
    private CardView fragProfile_CV_addPost;
    private CardView fragProfile_CV_logout;
    private User user;
    private UserController userController;
    private AppCompatActivity context;

    public ProfileFragment(AppCompatActivity context) {
        // Required empty public constructor
        this.context = context;
    }

    public void setUser(User user){
        this.user = user;
        this.displayUserInfo();
        updateProfileScreen();
    }

    public void displayUserInfo() {
        String fullName = user.getFirstName() + " " + user.getLastName();
        String email = user.getEmail();

        fragProfile_TV_name.setText(fullName);
        fragProfile_TV_email.setText(email);
    }

    public void updateProfileScreen(){
        if(!user.getAdmin()){
            fragProfile_CV_addPost.setVisibility(View.GONE);
        }else{
            fragProfile_CV_addPost.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fragProfile_TV_name = view.findViewById(R.id.fragProfile_TV_name);
        fragProfile_TV_email = view.findViewById(R.id.fragProfile_TV_email);
        fragProfile_CV_editDetails = view.findViewById(R.id.fragProfile_CV_editDetails);
        fragProfile_CV_addPost = view.findViewById(R.id.fragProfile_CV_addPost);
        fragProfile_CV_logout = view.findViewById(R.id.fragProfile_CV_logout);
    }

    private void initVars() {
        userController = new UserController();

        fragProfile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // logout user
                userController.logoutUser();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                context.finish();
            }
        });

        fragProfile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }
        });

        fragProfile_CV_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UploadPostActivity.class);
                intent.putExtra(USER_KEY, user);
                startActivity(intent);
            }
        });
    }
}