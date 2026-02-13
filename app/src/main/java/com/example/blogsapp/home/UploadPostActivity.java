package com.example.blogsapp.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blogsapp.R;
import com.example.blogsapp.callback.PostCallBack;
import com.example.blogsapp.database.Post;
import com.example.blogsapp.database.PostController;
import com.example.blogsapp.utils.ImagePicker;
import com.example.blogsapp.utils.SharedMethods;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;

import java.io.IOException;

public class UploadPostActivity extends AppCompatActivity {
    private ImagePicker imagePicker;

    private TextInputLayout post_TF_title;
    private TextInputLayout post_TF_description;
    private ImageView post_IV_image;
    private Button post_BTN_addPost;
    private CircularProgressIndicator post_PB_loading;
    private PostController postController;
    private String baseImage64 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        initVars();
    }

    private void findViews() {
        post_TF_title = findViewById(R.id.post_TF_title);
        post_TF_description = findViewById(R.id.post_TF_description);
        post_IV_image = findViewById(R.id.post_IV_image);
        post_BTN_addPost = findViewById(R.id.post_BTN_addPost);
        post_PB_loading = findViewById(R.id.post_PB_loading);
    }

    private void initVars() {

        imagePicker = new ImagePicker(this, new ImagePicker.OnImagePickedListener() {
            @Override
            public void onImagePicked(Uri imageUri) {
                try{
                    post_IV_image.setImageURI(imageUri);
                    Bitmap selectedImage = SharedMethods.uriToBitmap(UploadPostActivity.this, imageUri);
                    baseImage64 = SharedMethods.convertImageToBase64(selectedImage);
                }catch (IOException err){

                }
            }
        });


        postController = new PostController();
        postController.setPostCallBack(new PostCallBack() {
            @Override
            public void onCreatePostComplete(Task<Void> task) {
                // TODO: handle when add post complete send notification to all users
                post_PB_loading.setVisibility(View.INVISIBLE);

                if(task.isSuccessful()){
                    Toast.makeText(UploadPostActivity.this, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(UploadPostActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });


        post_IV_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pick image from gallery
                imagePicker.pickImage();
            }
        });

        post_BTN_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInputs()) return;

                // add new post
                Post post = new Post();
                String title = post_TF_title.getEditText().getText().toString();
                String description = post_TF_description.getEditText().getText().toString();
                post.setTitle(title);
                post.setDescription(description);
                post.setTimestamp(Timestamp.now());
                post_PB_loading.setVisibility(View.VISIBLE);

                if(!baseImage64.isEmpty()){
                    post.setImage(baseImage64);
                }

                postController.createPost(post);
            }
        });


    }

    public boolean checkInputs() {
        String title = post_TF_title.getEditText().getText().toString();
        String description = post_TF_description.getEditText().getText().toString();
        if(title.isEmpty()){
            post_TF_title.setError("Title is required");
            return false;
        }

        if(description.isEmpty()){
            post_TF_description.setError("Description is required");
            return false;
        }
        return true;
    }
}