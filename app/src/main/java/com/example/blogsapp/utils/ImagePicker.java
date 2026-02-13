package com.example.blogsapp.utils;


import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ImagePicker {
    public interface OnImagePickedListener {
        void onImagePicked(Uri imageUri);
    }

    private final ActivityResultLauncher<String> launcher;

    public ImagePicker(AppCompatActivity activity, OnImagePickedListener listener) {

        launcher = activity.registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null && listener != null) {
                        listener.onImagePicked(uri);
                    }
                }
        );
    }

    public void pickImage() {
        launcher.launch("image/*");
    }
}
