package com.kuymakov.mylibrary.ui.booksreader

import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.databinding.ReaderPageBinding
import com.kuymakov.mylibrary.ui.PdfReader

class PageHolder(private val binding: ReaderPageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun openPage(page: Int, pdfReader: PdfReader) {
        pdfReader.openPage(page, binding.page)
    }
}