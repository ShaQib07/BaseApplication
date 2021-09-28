package com.shakib.baseapplication.data.model

data class GameResponse(
    val count: Int,
    val next: String,
    val results: List<Game>
)