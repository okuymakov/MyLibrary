package com.example.mylibrary.ui.bookslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.R
import com.example.mylibrary.databinding.BooksItemBinding
import com.example.mylibrary.models.Book
import com.squareup.picasso.Picasso

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {
    private val books = mutableListOf<Book>()

    class ViewHolder(binding: BooksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val booksTitle: TextView = binding.title
        val booksAuthors: TextView = binding.authors
        val booksImage: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.booksTitle.text = book.bookInfo.title
        holder.booksAuthors.text = book.bookInfo.authors?.joinToString(", ") ?: ""



        Picasso.get().load(book.bookInfo.imageLink?.url).fit()
            .placeholder(R.drawable.book_placeholder)
            .into(holder.booksImage)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun addBooks(books: List<Book>) {
        this.books += books
        notifyDataSetChanged()
    }

    fun updateBooks(books: List<Book>) {
        this.books.clear()
        this.books += books
        notifyDataSetChanged()
    }
}