package com.shakib.baseapplication.data.repository

import com.shakib.baseapplication.data.network.StackoverflowApi
import com.shakib.baseapplication.data.mapper.QuestionListMapper
import com.shakib.baseapplication.data.model.QuestionsListResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchQuestionListRepo @Inject constructor(
    private val stackoverflowApi: StackoverflowApi,
    private val questionListMapper: QuestionListMapper
) {
    fun fetchQuestionList(): Single<QuestionsListResponse> =
        stackoverflowApi.fetchQuestionList(20).flatMap { questionListMapper.mapQuestionList(it) }
}