package com.shakib.baseapplication.data.room.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.shakib.baseapplication.data.model.PageKey

@Dao
interface PageDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAllPageKeys(pageKeyKey: List<PageKey>)

    @Query("SELECT * FROM pagekey WHERE `key` = :key")
    suspend fun fetchPageKey(key: Int): PageKey?

    @Query("DELETE FROM pagekey")
    suspend fun clearPageKeys()

}