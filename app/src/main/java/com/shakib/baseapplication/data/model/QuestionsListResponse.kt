package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName

data class QuestionsListResponse(@SerializedName("items") val questions: List<Question>)