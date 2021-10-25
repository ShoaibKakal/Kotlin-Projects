package com.shoaib.astroproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.shoaib.astroproject.databinding.ActivityNavBinding


class NavActivity : AppCompatActivity() {
    lateinit var navBinding: ActivityNavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navBinding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(navBinding.root)
        setUpNavigation()
    }




   private fun setUpNavigation() {
        val bottomNavigationView = navBinding.bttmNav
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment!!.navController
        )
    }
}