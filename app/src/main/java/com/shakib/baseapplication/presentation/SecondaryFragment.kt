package com.shakib.baseapplication.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.databinding.FragmentSecondaryBinding

class SecondaryFragment : BaseFragment<FragmentSecondaryBinding>() {

    private val viewModel: DialogViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSecondaryBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tv.setTextColor(Color.BLUE)
        binding.tv.text = title
        configureClickListeners()
        configureObservers()
    }

    private fun configureClickListeners() {
        binding.btn.setOnClickListener {
            findNavController().navigate(
                SimpleDialogDirections.secondaryToDialog(
                    "Dialog Title",
                    "Dialog Message"
                )
            )
        }
    }

    private fun configureObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            binding.tv.text = it
        })
    }

}
