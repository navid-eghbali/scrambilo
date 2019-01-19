package me.navid.scrambilo.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.navid.scrambilo.R

class MainActivity : DaggerAppCompatActivity(), ToolbarManager {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_main)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbarTitle.text = destination.label
        }
    }

    override fun setToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_main).navigateUp()
}
