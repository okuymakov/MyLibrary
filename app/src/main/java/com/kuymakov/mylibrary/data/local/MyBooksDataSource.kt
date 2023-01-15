package com.kuymakov.mylibrary.data.local

import com.kuymakov.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow

interface MyBooksDataSource {
    fun getBooks(): Flow<List<MyBook>>
    suspend fun addBooks(vararg books: MyBook)
    suspend fun deleteBooks(vararg books: MyBook)
    suspend fun updateBook(book: MyBook)
}