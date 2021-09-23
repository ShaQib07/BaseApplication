package com.shakib.baseapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Question(
        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String,

        @PrimaryKey
        @ColumnInfo(name = "question_id")
        @SerializedName("question_id")
        val id: String
): Serializable