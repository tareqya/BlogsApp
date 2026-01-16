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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private FrameLayout home_frame_research;
    private FrameLayout home_frame_profile;
    private FrameLayout home_frame_home;
    private BottomNavigationView home_BN;
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_home, homeFragment).commit();
        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_profile, profileFragment).commit();
        researchFragment = new ResearchFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.home_frame_research, researchFragment).commit();

        home_frame_research.setVisibility(View.INVISIBLE);
        home_frame_profile.setVisibility(View.INVISIBLE);
        home_frame_home.setVisibility(View.VISIBLE);



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