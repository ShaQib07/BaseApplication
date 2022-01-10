package com.shakib.baseapplication.data.network

import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.GameResponse
import com.shakib.baseapplication.data.model.ScreenshotResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {

    @GET("games")
    suspend fun getGamesPageByPage(
        @Query("dates") date: String,
        @Query("ordering") order: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): GameResponse

    @GET("games/{id}")
    suspend fun getGameDetailsById(
        @Path("id") id: Int
    ): Game

    @GET("games/{id}/screenshots")
    suspend fun getGameScreenshotById(
        @Path("id") id: Int
    ): ScreenshotResponse
}