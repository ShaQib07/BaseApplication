package com.shakib.baseapplication.common.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.shakib.baseapplication.R
import com.shakib.baseapplication.presentation.navigator.DialogNavigator
import com.shakib.baseapplication.presentation.navigator.ScreenNavigator
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    protected lateinit var navController: NavController
    @Inject protected lateinit var screenNavigator: ScreenNavigator
    @Inject protected lateinit var dialogNavigator: DialogNavigator
    protected lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = getViewBinding()
        setContentView(binding.root)
        init(savedInstanceState)
    }

    open fun init(savedInstanceState: Bundle?) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        configureViews()
        bindWithViewModel()
    }

    open fun configureViews() {}

    open fun bindWithViewModel() {
        // TODO - need to work with base ViewModel first, then come back here again
    }
}