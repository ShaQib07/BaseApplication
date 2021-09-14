package com.shakib.baseapplication.domain

import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.data.repository.FetchQuestionListRepo
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchQuestionListUseCase @Inject constructor(
    private val fetchQuestionListRepo: FetchQuestionListRepo
) {
    fun fetchQuestionListRx(): Single<QuestionsListResponse> =
        fetchQuestionListRepo.fetchQuestionListRx()

    suspend fun fetchQuestionListFlow(): Flow<QuestionsListResponse> =
        fetchQuestionListRepo.fetchQuestionListFlow()
}