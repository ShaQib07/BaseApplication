package com.shakib.baseapplication.data.mapper

import com.shakib.baseapplication.data.model.QuestionsListResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Single.create
import retrofit2.Response
import javax.inject.Inject

class QuestionListMapper @Inject constructor() {
    fun mapQuestionList(questionsListResponse: Response<QuestionsListResponse>): Single<QuestionsListResponse> =
        create { singleEmitter ->
            if (!singleEmitter.isDisposed){
                if (questionsListResponse.isSuccessful)
                    questionsListResponse.body()?.let { singleEmitter.onSuccess(it) }
                else
                    singleEmitter.onError(Throwable(questionsListResponse.message()))
            }
        }
}
