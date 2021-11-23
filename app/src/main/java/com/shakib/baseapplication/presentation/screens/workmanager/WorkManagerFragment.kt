package com.shakib.baseapplication.presentation.screens.workmanager

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.gone
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.extensions.visible
import com.shakib.baseapplication.databinding.FragmentWorkManagerBinding
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WorkManagerFragment : BaseFragment<FragmentWorkManagerBinding>() {

    private val viewModel: WorkManagerViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWorkManagerBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        binding.btnImageDownload.setOnClickListener { downloadWithPermission() }
        binding.btnQueryWork.setOnClickListener { queryWorkInfo() }
    }

    override fun bindWithViewModel() {
        super.bindWithViewModel()
        viewModel.requestIdLiveData.observe(viewLifecycleOwner, { observeWork(it) })
    }

    private fun downloadWithPermission() {
        RxPermissions(this)
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted) {
                    viewModel.showProgress()
                    binding.downloadLayout.gone()
                    viewModel.createOneTimeWorkRequest()
                    //createPeriodicWorkRequest()
                    //createDelayedWorkRequest()
                } else
                    requireContext().showLongToast("|| Permission Denied ||")
            }
    }

    private fun playDownloadedVideo(path: String) {
        binding.completeLayout.visible()
        binding.downloadLayout.gone()
        viewModel.hideProgress()
        binding.videoDownloaded.apply {
            setVideoPath(path)
            requestFocus()
            start()
        }
    }

    private fun observeWork(id: UUID) {
        viewModel.workManager.getWorkInfoByIdLiveData(id)
            .observe(this, { info ->
                if (info != null && info.state.isFinished) {
                    viewModel.hideProgress()
                    binding.downloadLayout.visible()
                    val path = info.outputData.getString("DATA")
                    if (path != null)
                        playDownloadedVideo(path)
                }
            })
    }

    private fun queryWorkInfo() {
        viewModel.workManager.getWorkInfosLiveData(viewModel.workQuery).observe(this) { workInfo ->
            binding.tvWorkInfo.visible()
            binding.tvWorkInfo.text =
                resources.getQuantityString(
                    R.plurals.text_work_desc, workInfo.size,
                    workInfo.size
                )
        }
    }
}