package com.shakib.baseapplication.presentation.screens.details

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.ScreenShot
import com.shakib.baseapplication.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import android.text.method.ScrollingMovementMethod
import com.shakib.baseapplication.common.extensions.visible


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        data?.toInt()?.let {
            viewModel.fetchGameDetails(it)
            viewModel.gameScreenShotsLiveData.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Loading -> viewModel.showProgress()
                    is Resource.Success -> configureImageSlider(response.data)
                    is Resource.Error -> {
                        context?.showLongToast(response.throwable.message.toString())
                        viewModel.hideProgress()
                    }
                }
            })
            viewModel.gameDetailsLiveData.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Loading -> viewModel.showProgress()
                    is Resource.Success -> setDescription(response.data.description)
                    is Resource.Error -> {
                        context?.showLongToast(response.throwable.message.toString())
                        viewModel.hideProgress()
                    }
                }
            })
        }
    }

    private fun configureImageSlider(imageList: List<ScreenShot>) {
        viewModel.hideProgress()
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