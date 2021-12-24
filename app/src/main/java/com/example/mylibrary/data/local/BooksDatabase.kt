package com.example.mylibrary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mylibrary.models.MyBook

@Database(entities = [MyBook::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun myBooksDao(): MyBooksDao

    companion object{
        const val DB_NAME = "Books"
    }
}