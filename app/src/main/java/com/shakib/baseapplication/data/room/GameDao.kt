package com.shakib.baseapplication.data.room

import androidx.room.*
import com.shakib.baseapplication.data.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    suspend fun fetchFavGameList(): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)
}