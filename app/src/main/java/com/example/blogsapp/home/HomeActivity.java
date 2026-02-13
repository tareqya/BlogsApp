package com.example.blogsapp.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.blogsapp.R;
import com.example.blogsapp.callback.UserCallBack;
import com.example.blogsapp.database.User;
import com.example.blogsapp.database.UserController;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private FrameLayout home_frame_research;
    private FrameLayout home_frame_profile;
    private FrameLayout home_frame_home;
    private BottomNavigationView home_BN;
    private UserController userController;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private ResearchFragment researchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        initVars();
    }
    private void findViews() {
        home_frame_research = findViewById(R.id.home_frame_research);
        home_frame_profile = findViewById(R.id.home_frame_profile);
        home_frame_home = findViewById(R.id.home_frame_home);
        home_BN = findViewById(R.id.home_BN);
    }
    private void initVars() {
        userController = new UserController();
        userController.setUserCallBack(new UserCallBack() {
            @Override
            public void onCreateUserComplete(Task<Void> task) {

            }

            @Override
            public void onGetUserComplete(User user) {
                // send the user to fragments
//                homeFragment.setUser(user);
                profileFragment.setUser(user);
                researchFragment.setUser(user);
            }
        });

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();
        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        researchFragment = new ResearchFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_research, researchFragment).commit();

        home_frame_research.setVisibility(View.INVISIBLE);
        home_frame_profile.setVisibility(View.INVISIBLE);
        home_frame_home.setVisibility(View.VISIBLE);

        String uid = userController.getCurrentUser().getUid();
        userController.getUserInfo(uid);

        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.home) {
                    // on home page
                    home_frame_research.setVisibility(View.INVISIBLE);
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.VISIBLE);
                }else if(menuItem.getItemId() == R.id.research) {
                    // on research page
                    home_frame_research.setVisibility(View.VISIBLE);
                    home_frame_profile.setVisibility(View.INVISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }else {
                    // on profile page
                    home_frame_research.setVisibility(View.INVISIBLE);
                    home_frame_profile.setVisibility(View.VISIBLE);
                    home_frame_home.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }


}