package com.example.mylibrary.data.repositories

import com.example.mylibrary.data.remote.BooksService
import com.example.mylibrary.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(private val booksService: BooksService) :
    BooksRepository {

    override suspend fun fetchBooks(query: String) = flow {
        emit(Response.Loading)
        try {
            Timber.d("Start fetching data from repo")
            val books = booksService.getBooks(query)
            emit(Response.Success(books))
        } catch (ex: Exception) {
            Timber.e("Exception: $ex")
            emit(Response.Error(ex))
        }
    }.flowOn(Dispatchers.IO)
}