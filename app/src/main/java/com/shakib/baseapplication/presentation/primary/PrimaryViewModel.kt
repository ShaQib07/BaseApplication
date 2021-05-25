package com.shakib.baseapplication.presentation.primary

import com.google.gson.Gson
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.extensions.printDebugLog
import com.shakib.baseapplication.common.utils.SingleLiveEvent
import com.shakib.baseapplication.data.StackoverflowApi
import com.shakib.baseapplication.data.model.Question
import com.shakib.baseapplication.data.model.QuestionsListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PrimaryViewModel @Inject constructor(private val stackoverflowApi: StackoverflowApi) :
    BaseViewModel() {

    var questionListLiveData = SingleLiveEvent<List<Question>>()

    fun networkCall() {
        stackoverflowApi.lastActiveQuestions(20).enqueue(object : Callback<QuestionsListResponse> {
            override fun onResponse(
                call: Call<QuestionsListResponse>,
                response: Response<QuestionsListResponse>
            ) {
                if (response.isSuccessful) {
                    printDebugLog(Gson().toJson(response.body()))
                    questionListLiveData.value = response.body()?.questions
                }
            }

            override fun onFailure(call: Call<QuestionsListResponse>, t: Throwable) {
                printDebugLog(t.message.toString())
            }
        })
    }

    override fun onClear() {}
}