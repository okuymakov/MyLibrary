package com.example.mylibrary.ui.mybooks

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.databinding.MyBooksItemBinding
import com.example.mylibrary.models.MyBook


class MyBooksAdapter(
    private val getPdfReader: (String) -> PdfReader,
    private val onLongClick: (MyBook) -> Unit
) : RecyclerView.Adapter<MyBooksAdapter.ViewHolder>() {
    private val books = mutableListOf<MyBook>()

    inner class ViewHolder(binding: MyBooksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val booksTitle: TextView = binding.title
        private val booksImage: ImageView = binding.imageView
        private var book: MyBook? = null

        fun bind(book: MyBook) {
            this.book = book
            booksTitle.text = book.title
            val pdfReader = getPdfReader(book.uri)
            pdfReader.openPage(0, booksImage)
        }

        init {
            binding.root.setOnLongClickListener {
                book?.let { book -> onLongClick(book) }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyBooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun updateData(newBooks: List<MyBook>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
}