package com.example.mylibrary.ui.mybooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.data.local.MyBooksDataSource
import com.example.mylibrary.models.MyBook
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

    fun deleteBooks(book: MyBook) {
        viewModelScope.launch {
            dataSource.deleteBook(book)
        }
    }

    val books: StateFlow<List<MyBook>> =
        dataSource.getBooks()
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, listOf<MyBook>())

}