package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.data.repository.QuestionListRepo
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchQuestionListUseCase @Inject constructor(private val questionListRepo: QuestionListRepo) {
    fun fetchQuestionListRx(): Single<QuestionsListResponse> =
        questionListRepo.fetchQuestionListRx()

    suspend fun fetchQuestionListFlow(): Flow<QuestionsListResponse> =
        questionListRepo.fetchQuestionListFlow()
}