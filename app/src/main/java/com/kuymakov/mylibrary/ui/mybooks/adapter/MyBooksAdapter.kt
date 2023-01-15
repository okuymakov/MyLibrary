package com.kuymakov.mylibrary.ui.mybooks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kuymakov.mylibrary.base.extensions.notifyChanges
import com.kuymakov.mylibrary.base.recyclerview.BaseAdapter
import com.kuymakov.mylibrary.databinding.MyBooksItemBinding
import com.kuymakov.mylibrary.models.MyBook
import com.kuymakov.mylibrary.ui.mybooks.ClickHandler


class MyBooksAdapter(
    private val clickHandler: ClickHandler<MyBook>,
) : BaseAdapter<MyBooksViewHolder, MyBook>() {

    override val data = mutableListOf<MyBook>()
    val allSelectedItems get() = data.filter { book -> book.isSelected }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBooksViewHolder {
        val binding = MyBooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyBooksViewHolder(binding, clickHandler)
    }

    fun updateData(newBooks: List<MyBook>) {
        val oldBooks = data.map { book -> book.fullCopy() }
        data.clear()
        data.addAll(newBooks)
        notifyChanges(oldBooks, newBooks)
    }

    fun selectBook(book: MyBook, select: Boolean = true) {
        book.isSelected = select
        notifyItemChanged(data.indexOf(book))
    }

    fun selectAll(select: Boolean = true) {
        val oldBooks = data.map { book -> book.fullCopy() }
        if (select) {
            data.forEach { book -> book.isSelected = select }
        } else {
            allSelectedItems.forEach { book -> book.isSelected = select }
        }
        notifyChanges(oldBooks, data)
    }

    fun isExist(item: MyBook) = data.contains(item)
}


