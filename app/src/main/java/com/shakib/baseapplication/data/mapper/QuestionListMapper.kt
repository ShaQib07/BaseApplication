package com.shakib.baseapplication.data.mapper

import com.shakib.baseapplication.data.model.QuestionsListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class QuestionListMapper @Inject constructor() {
    fun mapQuestionList(questionsListResponse: Response<QuestionsListResponse>): Single<QuestionsListResponse> =
        Single.create { it.onSuccess(questionsListResponse.body()) }
}