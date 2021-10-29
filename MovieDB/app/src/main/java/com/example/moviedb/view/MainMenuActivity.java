package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.moviedb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavbarMainMenu;
    private NavHostFragment navHostFragment;
    private Toolbar toolbarMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initView();
        setAppBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navHostFragment.getNavController().navigateUp() || super.onSupportNavigateUp();
    }

    private void initView(){
        bottomNavbarMainMenu = findViewById(R.id.bottomNavbarMainMenu);
        toolbarMainMenu = findViewById(R.id.toolbarMainMenu);
        setSupportActionBar(toolbarMainMenu);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navFragmentMainMenu);
    }

    private void setAppBar(){
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.nowPlayingFragment, R.id.upcomingFragment).build();
        NavigationUI.setupActionBarWithNavController(MainMenuActivity.this, navHostFragment.getNavController(), appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavbarMainMenu, navHostFragment.getNavController());
    }
}