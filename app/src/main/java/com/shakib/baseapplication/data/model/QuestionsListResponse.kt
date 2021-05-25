package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName
import com.shakib.baseapplication.data.model.Question

data class QuestionsListResponse(@SerializedName("items") val questions: List<Question>)