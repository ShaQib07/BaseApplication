package com.shakib.baseapplication.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakib.baseapplication.data.model.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM question")
    suspend fun fetchQuestionListRoom(): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)
}
