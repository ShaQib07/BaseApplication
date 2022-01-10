package com.shakib.baseapplication.data.room.question

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shakib.baseapplication.data.model.Question

@Database(entities = [Question::class], version = 1)
abstract class QuestionDB : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}