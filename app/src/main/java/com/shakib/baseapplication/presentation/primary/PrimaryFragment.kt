package com.shakib.baseapplication.presentation.primary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.showShortToast
import com.shakib.baseapplication.databinding.FragmentPrimaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrimaryFragment : BaseFragment<FragmentPrimaryBinding>() {

    private val viewModel: PrimaryViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPrimaryBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        viewModel.networkCall()
        viewModel.questionListLiveData.observe(viewLifecycleOwner, { questionList ->
            binding.rvQuestions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = QuestionListAdapter(questionList) {
                    context?.showShortToast(it?.title.toString())
                }
            }
        })
    }
}