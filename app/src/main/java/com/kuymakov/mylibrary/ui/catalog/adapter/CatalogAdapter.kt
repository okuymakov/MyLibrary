package com.kuymakov.mylibrary.ui.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.databinding.CatalogItemBinding
import com.kuymakov.mylibrary.models.BooksCategory

class CatalogAdapter(private val onClick: (BooksCategory) -> Unit) :
    RecyclerView.Adapter<CatalogViewHolder>() {
    private val categories = listOf(
        BooksCategory("Художественная литература","fiction", "https://books.google.com/books/content?id=2JKTEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
        BooksCategory("Наука","science", "https://books.google.com/books/content?id=QShEjwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
        BooksCategory("Биография","biography", "https://books.google.com/books/content?id=U84czgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
        BooksCategory("История", "history","https://books.google.com/books/content?id=8ZFfAAAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
        BooksCategory("Бизнес","business", "https://books.google.com/books/content?id=R9u8tAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
        BooksCategory("Детская литература", "children's","https://books.google.com/books/content?id=LM-rswEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = CatalogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}

