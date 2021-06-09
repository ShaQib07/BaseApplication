package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.StackoverflowApi
import com.shakib.baseapplication.data.model.QuestionsListResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchQuestionListUseCase @Inject constructor(private val stackoverflowApi: StackoverflowApi) {

    fun fetchQuestionList(): Single<QuestionsListResponse> =
        stackoverflowApi.fetchQuestionList(20).flatMap { response ->
            Single.create { it.onSuccess(response.body()) }
        }
}