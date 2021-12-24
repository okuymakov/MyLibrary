package com.example.mylibrary.data.local

import com.example.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow

interface MyBooksDataSource {
    fun getBooks(): Flow<List<MyBook>>
    suspend fun addBooks(vararg books: MyBook)
    suspend fun deleteBook(book: MyBook)
}