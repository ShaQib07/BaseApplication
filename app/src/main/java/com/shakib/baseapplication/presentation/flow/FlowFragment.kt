package com.shakib.baseapplication.presentation.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.extensions.showShortToast
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Question
import com.shakib.baseapplication.databinding.FragmentFlowBinding
import com.shakib.baseapplication.presentation.rx.QuestionListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                is Resource.Success -> configureRecyclerView(response.data)
                is Resource.Error -> {
                    lifecycleScope.launch { configureRecyclerView(viewModel.fetchCachedQuestionList()) }
                }
            }
        })
    }

    private fun configureRecyclerView(questions: List<Question>) {
        viewModel.hideProgress()
        if (questions.isNullOrEmpty())
            context?.showLongToast("Oops!! Something went wrong...")
        else
            binding.rvQuestions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = QuestionListAdapter(questions) {
                    context?.showShortToast(it?.title.toString())
                }
            }
    }
}
