package com.dicoding.dicodingevent

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.dicodingevent.databinding.ActivityMainBinding
import com.dicoding.dicodingevent.ui.theme.DarkModePreferences
import com.dicoding.dicodingevent.ui.theme.DarkModeViewModel
import com.dicoding.dicodingevent.ui.theme.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var darkModeViewModel: DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get dark mode
        darkModeData()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                 R.id.navigation_available_event, R.id.navigation_finished_event,R.id.favorite_nav
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun darkModeData() {
        // get preferences
        val preferences = DarkModePreferences.getInstance(dataStore)

        // init ViewModel
        darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(preferences))[DarkModeViewModel::class.java]

        // Observe data for Dark Mode
        darkModeViewModel.getDarkModeSetting().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}