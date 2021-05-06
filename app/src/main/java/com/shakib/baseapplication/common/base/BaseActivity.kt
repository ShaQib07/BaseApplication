package com.shakib.baseapplication.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = getViewBinding()
        setContentView(binding.root)
        init(savedInstanceState)
    }

    open fun init(savedInstanceState: Bundle?) {
        configureViews()
        bindWithViewModel()
    }

    open fun configureViews() {}

    open fun bindWithViewModel() {
        // TODO - need to work with base ViewModel first, then come back here again
    }
}