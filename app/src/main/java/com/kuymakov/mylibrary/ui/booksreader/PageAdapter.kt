package com.kuymakov.mylibrary.ui.booksreader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.databinding.ReaderPageBinding
import com.kuymakov.mylibrary.ui.PdfReader

class PageAdapter(private val pdfReader: PdfReader) : RecyclerView.Adapter<PageHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        val binding = ReaderPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PageHolder(binding)
    }

    override fun getItemCount() = pdfReader.pageCount


    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.openPage(position, pdfReader)

    }
}