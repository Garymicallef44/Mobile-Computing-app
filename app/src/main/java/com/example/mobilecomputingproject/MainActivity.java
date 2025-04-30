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
    // Viewbinding instance accesses views in activity_main.xml without findViewById
    private ActivityMainBinding binding;
    private TrackLibViewModel trackLibViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Delete existing database on startup (to refresh each time), returns true if deletion is successful
        boolean deleted = deleteDatabase(TrackHelper.DATABASE_NAME);
        Log.d("MainActivity","database deleted:" + deleted);

        // Inflate layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtains view model
        trackLibViewModel = new ViewModelProvider(this)
                .get(TrackLibViewModel.class);
        // Configure the AppBar to work with NavController destination
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_playlists,
                R.id.navigation_profile
        ).build();
        // Find navcontroller from the navhostfragment in activity_main.xml
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_activity_main
        );
        NavigationUI.setupActionBarWithNavController(
                this, navController, appBarConfiguration
        );
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
