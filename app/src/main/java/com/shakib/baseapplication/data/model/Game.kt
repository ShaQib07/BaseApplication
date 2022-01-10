package com.shakib.baseapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Game(
        @ColumnInfo(name = "background_image")
        @SerializedName("background_image")
        val backgroundImage: String,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: Int,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,

        @ColumnInfo(name = "isFavorite")
        val isFavorite: Boolean = false
) : Serializable {
    @Ignore
    var screenShots: MutableList<ScreenShot>? = null

    @Ignore
    @SerializedName("clip")
    var video: Clip? = null

    @Ignore
    val rating: Float? = null

    @Ignore
    val released: String? = null

    @Ignore
    val description: String? = null
}