package com.shakib.baseapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.shakib.baseapplication.common.base.BaseDialogFragment
import com.shakib.baseapplication.databinding.DialogProgressBinding

class ProgressDialog : BaseDialogFragment<DialogProgressBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogProgressBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}