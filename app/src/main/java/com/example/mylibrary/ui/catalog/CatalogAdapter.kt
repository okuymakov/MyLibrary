package com.example.mylibrary.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.R
import com.example.mylibrary.databinding.CatalogItemBinding
import com.example.mylibrary.models.BooksCategory

class CatalogAdapter(private val onClick: (BooksCategory) -> Unit) :
    RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {
    private val categories = listOf(
        BooksCategory("Художественная литература", R.drawable.book_placeholder),
        BooksCategory("Наука", R.drawable.book_placeholder),
        BooksCategory("Биография", R.drawable.book_placeholder),
        BooksCategory("История", R.drawable.book_placeholder),
        BooksCategory("Бизнес", R.drawable.book_placeholder),
        BooksCategory("Детская литература", R.drawable.book_placeholder),
    )

    inner class ViewHolder(binding: CatalogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.catalogImage
        private val name = binding.catalogTitle
        private var booksCategory: BooksCategory? = null

        fun bind(category: BooksCategory) {
            booksCategory = category
            name.text = category.name
            image.setImageResource(category.image)
        }

        init {
            binding.root.setOnClickListener {
                booksCategory?.let { onClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CatalogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}