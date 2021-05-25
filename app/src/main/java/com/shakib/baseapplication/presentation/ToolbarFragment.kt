package com.shakib.baseapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.databinding.FragmentToolbarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolbarFragment : BaseFragment<FragmentToolbarBinding>() {

    private val viewModel: DialogViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentToolbarBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tv.setTextColor(Color.BLUE)
        binding.tv.text = title
        configureClickListeners()
        configureObservers()
    }

    private fun configureClickListeners() {
        binding.btn.setOnClickListener {
            dialogNavigator.toSimpleDialog(
                findNavController(),
                "Dialog Title",
                "Dialog Message"
            )
        }
    }

    private fun configureObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            binding.tv.text = it
        })
    }

}
