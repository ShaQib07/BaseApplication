package com.shakib.baseapplication.data.network

import com.shakib.baseapplication.data.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackOverflowApi {

    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    fun fetchQuestionListRx(@Query("pagesize") pageSize: Int?): Single<Response<QuestionsListResponse>>

    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    suspend fun fetchQuestionListFlow(@Query("pagesize") pageSize: Int?): QuestionsListResponse

    @GET("/questions/{questionId}?site=stackoverflow&filter=withbody")
    fun questionDetails(@Path("questionId") questionId: String?): Call<SingleQuestionResponse>
}