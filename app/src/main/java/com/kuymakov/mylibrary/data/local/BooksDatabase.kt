package com.kuymakov.mylibrary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuymakov.mylibrary.models.MyBook

@Database(entities = [MyBook::class], version = 1, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun myBooksDao(): MyBooksDao

    companion object{
        const val DB_NAME = "Books"
    }
}