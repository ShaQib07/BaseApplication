package com.shakib.baseapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageKey(
    @PrimaryKey
    val key: Int,
    val prevKey: Int?,
    val nextKey: Int?
)