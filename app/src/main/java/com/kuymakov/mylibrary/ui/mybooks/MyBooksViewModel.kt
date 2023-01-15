package com.kuymakov.mylibrary.ui.mybooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuymakov.mylibrary.data.local.MyBooksDataSource
import com.kuymakov.mylibrary.models.MyBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBooksViewModel @Inject constructor(private val dataSource: MyBooksDataSource) :
    ViewModel() {

    fun addBooks(vararg books: MyBook) {
        viewModelScope.launch {
            dataSource.addBooks(*books)
        }
    }

    fun updateBook(book: MyBook) {
        viewModelScope.launch {
            dataSource.updateBook(book)
        }
    }

    fun deleteBooks(vararg books: MyBook) {
        viewModelScope.launch {
            dataSource.deleteBooks(*books)
        }
    }

    val books: StateFlow<List<MyBook>> =
        dataSource.getBooks()
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, listOf())

}