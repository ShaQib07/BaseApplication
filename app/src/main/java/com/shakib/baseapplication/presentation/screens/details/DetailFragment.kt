package com.shakib.baseapplication.presentation.screens.details

import android.os.Build
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.*
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.ScreenShot
import com.shakib.baseapplication.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun bindWithViewModel() {
        super.bindWithViewModel()
        data?.toInt()?.let {
            viewModel.fetchGameDetails(it)
            lifecycleScope.launch {
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    launch {
                        viewModel.gameScreenShotsStateFlow.collect { resource ->
                            when (resource) {
                                is Resource.Loading -> printDebugLog("Show Loading")
                                is Resource.Success -> configureImageSlider(resource.data)
                                is Resource.Error -> printErrorLog(resource.throwable.message.toString())
                            }
                        }
                    }
                    launch {
                        viewModel.gameDetailsStateFlow.collect { response ->
                            when (response) {
                                is Resource.Loading -> viewModel.showProgress()
                                is Resource.Success -> setDescription(response.data.description)
                                is Resource.Error -> {
                                    context?.showLongToast(response.throwable.message.toString())
                                    viewModel.hideProgress()
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun configureImageSlider(imageList: List<ScreenShot>) {
        binding.viewPager.apply {
            printInfoLog(Gson().toJson(imageList))
            adapter = ImageSliderAdapter(imageList)
            offscreenPageLimit = 2
        }
    }

    private fun setDescription(desc: String?) {
        viewModel.hideProgress()
        binding.divider.visible()
        binding.tvDesc.movementMethod = ScrollingMovementMethod()
        binding.tvDesc.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(desc)
    }
}