package com.shakib.baseapplication.presentation.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.extensions.showShortToast
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.databinding.FragmentFlowBinding
import com.shakib.baseapplication.presentation.rx.QuestionListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlowFragment : BaseFragment<FragmentFlowBinding>() {

    private val viewModel: FlowViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFlowBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        viewModel.fetchQuestionList()
        viewModel.questionListLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> viewModel.showProgress()
                is Resource.Success -> {
                    binding.rvQuestions.apply {
                        viewModel.hideProgress()
                        layoutManager = LinearLayoutManager(context)
                        adapter = QuestionListAdapter(response.data) {
                            context?.showShortToast(it?.title.toString())
                        }
                    }
                }
                is Resource.Error -> {
                    viewModel.hideProgress()
                    context?.showLongToast(response.throwable.message.toString())
                }
            }
        })
    }
}
