package com.shakib.baseapplication.presentation.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Question
import com.shakib.baseapplication.domain.FetchQuestionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(private val fetchQuestionListUseCase: FetchQuestionListUseCase) :
    BaseViewModel() {

    val questionListLiveData by lazy { MutableLiveData<Resource<List<Question>>>() }

    fun fetchQuestionList() {
        viewModelScope.launch {
            questionListLiveData.value = Resource.Loading()
            fetchQuestionListUseCase.fetchQuestionListFlow()
                .catch {
                    questionListLiveData.value = Resource.Error(it)
                }
                .collect {
                    questionListLiveData.value = Resource.Success(it.questions)
                }
        }
    }

    override fun onClear() {}
}