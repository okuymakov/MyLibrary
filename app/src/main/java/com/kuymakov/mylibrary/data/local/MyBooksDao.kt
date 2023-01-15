package com.kuymakov.mylibrary.data.local

import androidx.room.*
import com.kuymakov.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow

@Dao
interface MyBooksDao {

    @Query("SELECT * FROM MyBook")
    fun getAll(): Flow<List<MyBook>>

    @Delete
    suspend fun delete(vararg books: MyBook)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg books: MyBook)

    @Update
    suspend fun update(book: MyBook)
}