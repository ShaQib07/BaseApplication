package com.shakib.baseapplication.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.shakib.baseapplication.presentation.navigator.DialogNavigator
import com.shakib.baseapplication.presentation.navigator.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    @Inject protected lateinit var screenNavigator: ScreenNavigator
    @Inject protected lateinit var dialogNavigator: DialogNavigator
    var title: String? = null

    companion object {
        const val TITLE = "title"
    }

    protected lateinit var binding: VB

    protected abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractArguments()
        init(savedInstanceState)
    }

    open fun init(savedInstanceState: Bundle?) {
        configureViews(savedInstanceState)
    }

    open fun configureViews(savedInstanceState: Bundle?) {}

    open fun bindWithViewModel() {
        // TODO - need to work with base ViewModel first, then come back here again
    }

    private fun extractArguments() {
        arguments?.let {
            title = it.getString(TITLE)
        }
    }
}