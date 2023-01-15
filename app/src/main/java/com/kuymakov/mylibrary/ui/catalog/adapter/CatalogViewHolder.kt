package com.kuymakov.mylibrary.ui.catalog.adapter

import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.databinding.CatalogItemBinding
import com.kuymakov.mylibrary.models.BooksCategory
import com.squareup.picasso.Picasso

class CatalogViewHolder(
    binding: CatalogItemBinding,
    private val onClick: (BooksCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val image = binding.catalogImage
    private val name = binding.catalogTitle
    private var booksCategory: BooksCategory? = null

    fun bind(category: BooksCategory) {
        booksCategory = category
        name.text = category.displayName
        Picasso.get().load(category.image).fit()
            .placeholder(R.drawable.book_placeholder)
            .into(image)
    }

    init {
        binding.root.setOnClickListener {
            booksCategory?.let { onClick(it) }
        }
    }
}