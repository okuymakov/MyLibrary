package com.example.mylibrary.data.local

import com.example.mylibrary.models.MyBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyBooksDataSourceImpl @Inject constructor(private val db: MyBooksDao) : MyBooksDataSource {
    override fun getBooks(): Flow<List<MyBook>> {
        return db.getAll()
    }

    override suspend fun addBooks(vararg books: MyBook) {
        db.insertAll(*books)
    }

    override suspend fun deleteBook(book: MyBook) {
        db.delete(book)
    }
}