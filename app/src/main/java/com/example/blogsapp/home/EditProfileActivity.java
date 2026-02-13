package com.example.blogsapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blogsapp.R;
import com.example.blogsapp.database.User;
import com.example.blogsapp.database.UserController;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {

    private User user;
    private TextInputLayout profileEdit_TF_firstName;
    private TextInputLayout profileEdit_TF_lastName;
    private Button editProfile_BTN_update;
    private CircularProgressIndicator editProfile_PB_loading;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra(ProfileFragment.USER_KEY);
        findViews();
        initVars();
    }

    private void findViews() {
        profileEdit_TF_firstName = findViewById(R.id.profileEdit_TF_firstName);
        profileEdit_TF_lastName = findViewById(R.id.profileEdit_TF_lastName);
        editProfile_BTN_update = findViewById(R.id.editProfile_BTN_update);
        editProfile_PB_loading = findViewById(R.id.editProfile_PB_loading);
    }

    private void initVars() {
        userController  = new UserController();
        profileEdit_TF_firstName.getEditText().setText(user.getFirstName());
        profileEdit_TF_lastName.getEditText().setText(user.getLastName());

        editProfile_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = profileEdit_TF_firstName.getEditText().getText().toString();
                String lastName = profileEdit_TF_lastName.getEditText().getText().toString();

                if(firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                editProfile_PB_loading.setVisibility(View.VISIBLE);

                user.setFirstName(firstName);
                user.setLastName(lastName);

                userController.updateUserInfo(user);
                editProfile_PB_loading.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }
}