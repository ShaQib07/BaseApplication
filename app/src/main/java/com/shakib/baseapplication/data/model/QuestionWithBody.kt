package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName
import com.shakib.baseapplication.data.model.User

data class QuestionWithBody(
        @SerializedName("data") val title: String,
        @SerializedName("question_id") val id: String,
        @SerializedName("body") val body: String,
        @SerializedName("owner") val owner: User
)