package com.shakib.baseapplication.presentation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.base.BaseActivity
import com.shakib.baseapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun configureViews() {
        super.configureViews()
        configureNavGraph()
        configureClickListeners()
    }

    private fun configureNavGraph() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun configureClickListeners() {
        binding.btnSettings.setOnClickListener {
            navController.navigate(
                SecondaryFragmentDirections.primaryToSecondary(
                    getString(R.string.hello_secondary_fragment)
                )
            )
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

}