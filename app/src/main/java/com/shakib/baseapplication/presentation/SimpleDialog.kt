package com.shakib.baseapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.shakib.baseapplication.common.base.BaseDialogFragment
import com.shakib.baseapplication.databinding.DialogSimpleBinding

class SimpleDialog : BaseDialogFragment<DialogSimpleBinding>() {

    private val viewModel: DialogViewModel by activityViewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogSimpleBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        binding.tvTitle.text = title
        binding.tvBody.text = message
        binding.btn.setOnClickListener {
            viewModel.data.value = "PASSED DATA"
            dismissAllowingStateLoss()
        }
    }
}