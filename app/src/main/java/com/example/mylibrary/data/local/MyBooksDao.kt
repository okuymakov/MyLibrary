package com.example.mylibrary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow

@Dao
interface MyBooksDao {

    @Query("SELECT * FROM MyBook")
    fun getAll(): Flow<List<MyBook>>

    @Delete
    suspend fun delete(book: MyBook)

    @Insert
    suspend fun insertAll(vararg books: MyBook)
}