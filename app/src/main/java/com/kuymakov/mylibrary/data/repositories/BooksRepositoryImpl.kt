package com.kuymakov.mylibrary.data.repositories

import com.kuymakov.mylibrary.data.remote.BooksService
import com.kuymakov.mylibrary.models.Books
import com.kuymakov.mylibrary.models.ImageLink
import com.kuymakov.mylibrary.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(private val booksService: BooksService) :
    BooksRepository {

    override suspend fun fetchBooks(query: String) = flow {
        emit(Response.Loading)
        try {
            Timber.d("Start fetching data from repo")
            val books = booksService.getBooks(query)
            val mapBooks = Books(books.books.map { b ->
                val url = b.bookInfo.imageLink?.url
                if (url != null) {
                    b.copy(bookInfo = b.bookInfo.copy(
                        imageLink = ImageLink(url.replace("http", "https")))
                    )
                } else b
            })
            emit(Response.Success(mapBooks))
        } catch (ex: Exception) {
            Timber.e("Exception: $ex")
            emit(Response.Error(ex))
        }
    }.flowOn(Dispatchers.IO)
}