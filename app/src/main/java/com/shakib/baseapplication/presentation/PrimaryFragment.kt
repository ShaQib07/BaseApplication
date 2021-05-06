package com.shakib.baseapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.databinding.FragmentPrimaryBinding

class PrimaryFragment : BaseFragment<FragmentPrimaryBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPrimaryBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tv.setTextColor(Color.BLUE)
    }
}