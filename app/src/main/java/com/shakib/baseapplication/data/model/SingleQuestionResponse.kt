package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName
import com.shakib.baseapplication.data.model.QuestionWithBody

data class SingleQuestionResponse(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}