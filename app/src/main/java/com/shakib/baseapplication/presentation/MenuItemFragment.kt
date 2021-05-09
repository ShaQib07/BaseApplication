package com.shakib.baseapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.databinding.FragmentMenuItemBinding

class MenuItemFragment : BaseFragment<FragmentMenuItemBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMenuItemBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tv.text = title
    }
}