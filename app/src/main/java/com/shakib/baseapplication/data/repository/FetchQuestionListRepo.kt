package com.shakib.baseapplication.data.repository

import com.shakib.baseapplication.common.di.ApiForFlow
import com.shakib.baseapplication.common.di.ApiForRx
import com.shakib.baseapplication.data.mapper.QuestionListMapper
import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.data.network.StackoverflowApi
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchQuestionListRepo @Inject constructor(
    @ApiForRx private val stackoverflowApiRx: StackoverflowApi,
    @ApiForFlow private val stackoverflowApiFlow: StackoverflowApi,
    private val questionListMapper: QuestionListMapper
) {
    fun fetchQuestionListRx(): Single<QuestionsListResponse> =
        stackoverflowApiRx.fetchQuestionListRx(20)
            .flatMap { questionListMapper.mapQuestionList(it) }

    suspend fun fetchQuestionListFlow(): Flow<QuestionsListResponse> =
        flow { emit(stackoverflowApiFlow.fetchQuestionListFlow(20)) }
}