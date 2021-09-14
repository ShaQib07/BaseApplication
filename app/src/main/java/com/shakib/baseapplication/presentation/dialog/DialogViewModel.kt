package com.shakib.baseapplication.presentation.dialog

import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.utils.SingleLiveEvent

class DialogViewModel : BaseViewModel() {
    val data = SingleLiveEvent<String>()

    override fun onClear() {}
}