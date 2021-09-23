package com.shakib.baseapplication.common.di

import android.content.Context
import androidx.room.Room
import com.shakib.baseapplication.data.room.QuestionDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideQuestionDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, QuestionDB::class.java, "Question").build()

    @Provides
    fun provideQuestionDao(questionDB: QuestionDB) = questionDB.questionDao()
}