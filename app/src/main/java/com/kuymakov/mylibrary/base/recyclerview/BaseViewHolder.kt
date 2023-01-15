package com.kuymakov.mylibrary.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : Item<*>, R : ViewBinding>(
    binding: R,
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(item: T)
}