package com.shakib.baseapplication.presentation.screens.workmanager

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.gone
import com.shakib.baseapplication.common.extensions.visible
import com.shakib.baseapplication.databinding.FragmentWorkManagerBinding
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

        requestStoragePermissions()
        binding.btnImageDownload.setOnClickListener {
            viewModel.showProgress()
            binding.downloadLayout.gone()
            viewModel.createOneTimeWorkRequest()
            //createPeriodicWorkRequest()
            //createDelayedWorkRequest()
        }
        binding.btnQueryWork.setOnClickListener { queryWorkInfo() }
    }

    override fun bindWithViewModel() {
        super.bindWithViewModel()
        viewModel.requestIdLiveData.observe(viewLifecycleOwner, { observeWork(it) })
    }

    private fun requestStoragePermissions() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private fun showDownloadedImage(resultUri: Uri?) {
        binding.completeLayout.visible()
        binding.downloadLayout.gone()
        viewModel.hideProgress()
        binding.imgDownloaded.setImageURI(resultUri)
    }

    private fun observeWork(id: UUID) {
        viewModel.workManager.getWorkInfoByIdLiveData(id)
            .observe(this, { info ->
                if (info != null && info.state.isFinished) {
                    viewModel.hideProgress()
                    binding.downloadLayout.visible()
                    val uriResult = info.outputData.getString("IMAGE_URI")
                    if (uriResult != null) {
                        showDownloadedImage(uriResult.toUri())
                    }
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