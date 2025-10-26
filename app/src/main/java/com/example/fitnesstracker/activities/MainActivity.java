package com.example.profitness.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.example.profitness.R;
import com.example.profitness.fragments.DashboardFragment;
import com.example.profitness.fragments.HistoryFragment;
import com.example.profitness.fragments.ProfileFragment;
import com.example.profitness.utils.SharedPrefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupBottomNavigation();
        loadDashboardFragment();
        checkFirstRun();
    }

    private void initializeViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        sharedPrefs = SharedPrefs.getInstance(this);

        // Apply entrance animation
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        bottomNavigation.startAnimation(slideUp);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                loadDashboardFragment();
                return true;
            } else if (itemId == R.id.nav_history) {
                loadHistoryFragment();
                return true;
            } else if (itemId == R.id.nav_profile) {
                loadProfileFragment();
                return true;
            }
            return false;
        });
    }

    private void loadDashboardFragment() {
        replaceFragment(new DashboardFragment(), "dashboard");
    }

    private void loadHistoryFragment() {
        replaceFragment(new HistoryFragment(), "history");
    }

    private void loadProfileFragment() {
        replaceFragment(new ProfileFragment(), "profile");
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment, tag);
        transaction.commit();
    }

    private void checkFirstRun() {
        if (sharedPrefs.isFirstRun()) {
            // Perform first run setup
            sharedPrefs.setFirstRun(false);
        }
    }
}