package com.kuymakov.mylibrary.ui.bookslist.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.databinding.BooksItemBinding
import com.kuymakov.mylibrary.models.Book
import com.squareup.picasso.Picasso

class BooksViewHolder(binding: BooksItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val booksTitle: TextView = binding.title
    private val booksAuthors: TextView = binding.authors
    private val booksImage: ImageView = binding.imageView

    fun bind(book: Book) {
        booksTitle.text = book.bookInfo.title
        booksAuthors.text = book.bookInfo.authors?.joinToString(", ") ?: ""
        Picasso.get().load(book.bookInfo.imageLink?.url).fit()
            .placeholder(R.drawable.book_placeholder)
            .into(booksImage)
    }
}