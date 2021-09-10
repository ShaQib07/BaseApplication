package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.data.repository.FetchQuestionListRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchQuestionListUseCase @Inject constructor(
    private val fetchQuestionListRepo: FetchQuestionListRepo
) {
    fun fetchQuestionList(): Single<QuestionsListResponse> =
        fetchQuestionListRepo.fetchQuestionList()
}