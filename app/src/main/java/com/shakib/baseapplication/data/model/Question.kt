package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Question(
        @SerializedName("title") val title: String,
        @SerializedName("question_id") val id: String
): Serializable