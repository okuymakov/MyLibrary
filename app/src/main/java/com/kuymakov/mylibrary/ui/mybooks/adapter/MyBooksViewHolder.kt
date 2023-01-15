package com.kuymakov.mylibrary.ui.mybooks.adapter

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.recyclerview.BaseViewHolder
import com.kuymakov.mylibrary.databinding.MyBooksItemBinding
import com.kuymakov.mylibrary.models.MyBook
import com.kuymakov.mylibrary.ui.PdfReader
import com.kuymakov.mylibrary.ui.mybooks.ClickHandler

class MyBooksViewHolder(
    private val binding: MyBooksItemBinding,
    private val clickHandler: ClickHandler<MyBook>,
) : BaseViewHolder<MyBook,MyBooksItemBinding>(binding) {

    private val booksTitle: TextView = binding.title
    private val booksImage: ImageView = binding.imageView
    private var book: MyBook? = null

    init {
        binding.root.setOnLongClickListener {
            book?.let { book ->
                clickHandler.onLongClick?.let { onLongClick ->
                    onLongClick(book)
                }
            }
            true
        }
        binding.root.setOnClickListener {
            book?.let { book ->
                clickHandler.onClick?.let { onClick ->
                    onClick(book)
                }
            }
        }
    }

    override fun onBind(item: MyBook) {
        this.book = item

        val cardColor = if (item.isSelected) {
            ContextCompat.getColor(binding.root.context, R.color.cardview_dark_background)
        } else {
            ContextCompat.getColor(binding.root.context, R.color.cardview_light_background)
        }
        binding.root.setCardBackgroundColor(cardColor)
        booksTitle.text = item.title
        val textColor = if (item.isSelected) {
            ContextCompat.getColor(binding.root.context, R.color.white)
        } else {
            ContextCompat.getColor(binding.root.context, R.color.black)
        }
        binding.title.setTextColor(textColor)

        val contentResolver = booksImage.context.contentResolver
        val pdfReader = getPdfReader(item.uri, contentResolver)
        pdfReader.openPage(0, booksImage)
    }


    private fun getPdfReader(sUri: String, contentResolver: ContentResolver): PdfReader {
        val uri = Uri.parse(sUri)
        contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        return PdfReader(uri, contentResolver)
    }
}