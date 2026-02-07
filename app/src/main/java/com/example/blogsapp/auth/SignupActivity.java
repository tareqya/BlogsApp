package com.example.blogsapp.auth;

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
import com.example.blogsapp.callback.AuthCallBack;
import com.example.blogsapp.callback.UserCallBack;
import com.example.blogsapp.database.User;
import com.example.blogsapp.database.UserController;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private CircularProgressIndicator signup_PB_loading;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initVars();
    }

    private void initViews() {
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }

    private void initVars() {
        userController = new UserController();
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onCreateUserComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, "Account creation failed " + error, Toast.LENGTH_SHORT).show();
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onGetUserComplete(User user) {

            }
        });
        userController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onCreateAuthAccountComplete(Task<AuthResult> task) {
                // check if create auth account success
                if(task.isSuccessful()){
                    String uid = userController.getCurrentUser().getUid();
                    User user = new User()
                            .setAdmin(false)
                            .setEmail(signup_TF_email.getEditText().getText().toString())
                            .setFirstName(signup_TF_firstName.getEditText().getText().toString())
                            .setLastName(signup_TF_lastName.getEditText().getText().toString());
                    user.setUid(uid);
                    userController.createUserInfo(user);
                }else{
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, "Account creation failed " + error, Toast.LENGTH_SHORT).show();
                    signup_PB_loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }
        });

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateInputs()) return;
                signup_PB_loading.setVisibility(View.VISIBLE);

                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                userController.createAuthUser(email, password);
            }
        });
    }

    private boolean validateInputs(){
        if(signup_TF_firstName.getEditText().getText().toString().isEmpty()){
            signup_TF_firstName.setError("First name is required");
            return false;
        }
        if(signup_TF_lastName.getEditText().getText().toString().isEmpty()){
            signup_TF_lastName.setError("Last name is required");
            return false;
        }
        if(signup_TF_email.getEditText().getText().toString().isEmpty()){
            signup_TF_email.setError("Email is required");
            return false;
        }
        if(signup_TF_password.getEditText().getText().toString().isEmpty()){
            signup_TF_password.setError("Password is required");
            return false;
        }
        if(signup_TF_confirmPassword.getEditText().getText().toString().isEmpty()){
            signup_TF_confirmPassword.setError("Confirm password is required");
            return false;
        }
        if(!signup_TF_confirmPassword.getEditText().getText().toString().equals(signup_TF_password.getEditText().getText().toString())){
            signup_TF_confirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}