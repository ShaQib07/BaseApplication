package com.shakib.baseapplication.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clip(
        @SerializedName("video")
        val videoId: String
) : Serializable