package com.shakib.baseapplication.data

import com.shakib.baseapplication.data.model.QuestionsListResponse
import com.shakib.baseapplication.data.model.SingleQuestionResponse
import com.shakib.baseapplication.di.NetworkModule
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackoverflowApi {
    @GET("/questions?key=" + NetworkModule.STACKOVERFLOW_API_KEY + "&order=desc&sort=activity&site=stackoverflow")
    fun lastActiveQuestions(@Query("pagesize") pageSize: Int?): Call<QuestionsListResponse>

    @GET("/questions/{questionId}?key=" + NetworkModule.STACKOVERFLOW_API_KEY + "&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Call<SingleQuestionResponse>
}