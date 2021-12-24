package com.example.mylibrary.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.data.repositories.BooksRepository
import com.example.mylibrary.models.Books
import com.example.mylibrary.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: BooksRepository) : ViewModel() {
    private val _books = MutableStateFlow<Response<Books>>(Response.Success(Books(emptyList())))
    val books = _books.asStateFlow()

    fun fetchBooks(query: String) =
        viewModelScope.launch {
            Timber.d("Start fetching data")
            repo.fetchBooks(query).collect { value ->
                _books.value = value
            }
        }
}