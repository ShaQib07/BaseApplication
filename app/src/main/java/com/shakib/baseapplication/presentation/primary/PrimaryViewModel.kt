package com.shakib.baseapplication.presentation.primary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.extensions.printDebugLog
import com.shakib.baseapplication.common.extensions.printErrorLog
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Question
import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.domain.FetchQuestionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimaryViewModel @Inject constructor(private val fetchQuestionListUseCase: FetchQuestionListUseCase) :
    BaseViewModel() {

    val questionListLiveData by lazy { MutableLiveData<Resource<List<Question>>>() }

    fun fetchQuestionList() {
        viewModelScope.launch {
            compositeDisposable.add(
                fetchQuestionListUseCase.fetchQuestionList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<QuestionsListResponse>() {
                        override fun onStart() {
                            super.onStart()
                            questionListLiveData.value = Resource.Loading()
                        }

                        override fun onSuccess(t: QuestionsListResponse?) {
                            printDebugLog("RX response - " + Gson().toJson(t))
                            t?.questions?.let {
                                questionListLiveData.value = Resource.Success(it)
                            }
                        }

                        override fun onError(e: Throwable?) {
                            printErrorLog(e?.message.toString())
                            questionListLiveData.value = e?.let { Resource.Error(it) }
                        }
                    })
            )
        }
    }

    override fun onClear() {}
}