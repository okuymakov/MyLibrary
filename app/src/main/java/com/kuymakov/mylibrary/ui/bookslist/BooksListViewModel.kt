package com.kuymakov.mylibrary.ui.bookslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuymakov.mylibrary.data.repositories.BooksRepository
import com.kuymakov.mylibrary.models.Books
import com.kuymakov.mylibrary.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BooksListViewModel @Inject constructor(private val repo: BooksRepository) : ViewModel() {
    private val _books = MutableSharedFlow<Response<Books>>()
    val books = _books.asSharedFlow()

    fun fetchBooks(query: String) =
        viewModelScope.launch {
            Timber.d("Start fetching data")
            repo.fetchBooks(query).collect { value ->
                _books.emit(value)
            }
        }
}