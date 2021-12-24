package com.example.mylibrary.data.repositories

import com.example.mylibrary.models.Book
import com.example.mylibrary.models.Books
import com.example.mylibrary.models.Response
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun fetchBooks(query : String) : Flow<Response<Books>>
}