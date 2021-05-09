package com.shakib.baseapplication.presentation

import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
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
        checkContentsVisibility()
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
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.primaryFragment, R.id.secondaryFragment), binding.drawerLayout)

        // bind navigation component with navigation drawer
        binding.navigationView.setupWithNavController(navController)
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        // bind navigation component with bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        // bind navigation component with toolbar
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun checkContentsVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.menu.clear()
            binding.bottomNavigation.visibility = View.GONE
            val fragmentList = listOf(R.id.primaryFragment, R.id.secondaryFragment)
            if (fragmentList.contains(destination.id)) {
                binding.toolbar.menu.add(R.id.primary_menu, R.id.settings, 0, "").apply {
                    setShowAsAction(SHOW_AS_ACTION_ALWAYS)
                    icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_settings)
                }
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun configureClickListeners() {
        binding.navigationDrawer.apply {
            menuOne.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                navController.navigate(
                    MenuItemFragmentDirections.primaryToMenuItem(
                        "From Menu Item 1"
                    )
                )
            }
            menuTwo.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                navController.navigate(
                    MenuItemFragmentDirections.primaryToMenuItem(
                        "From Menu Item 2"
                    )
                )
            }
            menuThree.setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                navController.navigate(
                    MenuItemFragmentDirections.primaryToMenuItem(
                        "From Menu Item 3"
                    )
                )
            }
        }
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController, appBarConfiguration)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                navController.navigate(
                    ToolbarFragmentDirections.primaryToToolbar(
                        getString(R.string.hello_toolbar_fragment)
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) ->
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            else -> super.onBackPressed()
        }
    }
}