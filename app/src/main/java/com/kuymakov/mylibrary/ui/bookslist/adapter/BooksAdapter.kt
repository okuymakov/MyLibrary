package com.kuymakov.mylibrary.ui.bookslist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.databinding.BooksItemBinding
import com.kuymakov.mylibrary.models.Book

class BooksAdapter : RecyclerView.Adapter<BooksViewHolder>() {
    private val books = mutableListOf<Book>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = BooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBooks(books: List<Book>) {
        this.books += books
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateBooks(books: List<Book>) {
        this.books.clear()
        this.books += books
        notifyDataSetChanged()
    }
}