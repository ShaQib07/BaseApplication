package com.shakib.baseapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.Question

@Database(entities = [Question::class, Game::class], version = 2)
abstract class AppDB : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun gameDao(): GameDao
}