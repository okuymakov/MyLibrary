package com.kuymakov.mylibrary.data.local

import com.kuymakov.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyBooksDataSourceImpl @Inject constructor(private val db: MyBooksDao) : MyBooksDataSource {
    override fun getBooks(): Flow<List<MyBook>> {
        return db.getAll()
    }

    override suspend fun addBooks(vararg books: MyBook) {
        db.insertAll(*books)
    }

    override suspend fun deleteBooks(vararg books: MyBook) {
        db.delete(*books)
    }

    override suspend fun updateBook(book: MyBook) {
        db.update(book)
    }
}