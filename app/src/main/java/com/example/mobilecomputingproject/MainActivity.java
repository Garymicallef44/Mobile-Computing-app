package com.example.mobilecomputingproject;

import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilecomputingproject.data.TrackHelper;
import com.example.mobilecomputingproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TrackLibViewModel trackLibViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean deleted = deleteDatabase(TrackHelper.DATABASE_NAME);
        Log.d("MainActivity","database deleted:" + deleted);

        // 1) Inflate your layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trackLibViewModel = new ViewModelProvider(this)
                .get(TrackLibViewModel.class);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_playlists,
                R.id.navigation_profile
        ).build();
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_activity_main
        );
        NavigationUI.setupActionBarWithNavController(
                this, navController, appBarConfiguration
        );
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
