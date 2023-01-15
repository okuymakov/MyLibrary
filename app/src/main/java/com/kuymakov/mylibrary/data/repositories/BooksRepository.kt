package com.kuymakov.mylibrary.data.repositories

import com.kuymakov.mylibrary.models.Books
import com.kuymakov.mylibrary.models.Response
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun fetchBooks(query : String) : Flow<Response<Books>>
}