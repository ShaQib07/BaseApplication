package com.shakib.baseapplication.data.room.game

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.PageKey

@Database(entities = [Game::class, PageKey::class], version = 1)
abstract class GameDB : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun gameKeyDao(): PageDao
}