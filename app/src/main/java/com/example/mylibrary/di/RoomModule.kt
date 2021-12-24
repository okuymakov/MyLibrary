package com.example.mylibrary.di

import android.content.Context
import androidx.room.Room
import com.example.mylibrary.data.local.BooksDatabase
import com.example.mylibrary.data.local.MyBooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun provideDB(@ApplicationContext appContext: Context): BooksDatabase {
        return Room.databaseBuilder(
            appContext,
            BooksDatabase::class.java, BooksDatabase.DB_NAME
        ).build()
    }

    @Provides
    fun provideMyBooksDao(db: BooksDatabase) : MyBooksDao {
        return db.myBooksDao()
    }
}