package com.kuymakov.mylibrary.base.extensions

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kuymakov.mylibrary.base.recyclerview.BaseDiffUtilCallback
import com.kuymakov.mylibrary.base.recyclerview.Item

fun <T : Item<*>> RecyclerView.Adapter<*>.notifyChanges(
    oldData: List<T>,
    newData: List<T>
) {
    val callback = BaseDiffUtilCallback(oldData, newData)
    val diffResult = DiffUtil.calculateDiff(callback)
    diffResult.dispatchUpdatesTo(this)
}