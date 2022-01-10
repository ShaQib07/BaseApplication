package com.shakib.baseapplication.data.room.game

import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.shakib.baseapplication.data.model.Game

@Dao
interface GameDao {
    // For All Games
    @Insert(onConflict = REPLACE)
    suspend fun saveGameList(gameList: List<Game>)

    @Query("SELECT * FROM game")
    fun fetchGameList(): PagingSource<Int, Game>

    @Query("DELETE FROM game")
    suspend fun clearGames()

    // For Favorite Games
    @Query("SELECT * FROM game")
    suspend fun fetchFavGameList(): List<Game>

    @Insert(onConflict = REPLACE)
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)
}