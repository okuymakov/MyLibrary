package com.kuymakov.mylibrary.base.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<V : BaseViewHolder<T, *>, T : Item<*>> :
    RecyclerView.Adapter<V>() {
    protected abstract val data: List<T>

    override fun onBindViewHolder(holder: V, position: Int) {
        val item = data[position]
        holder.onBind(item)
    }

    override fun getItemCount() = data.size
}