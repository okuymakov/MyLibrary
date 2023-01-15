package com.kuymakov.mylibrary.models

import com.kuymakov.mylibrary.R

sealed class Tool(
    val name: String,
    val iconSrc: Int
) {
    object Read : Tool("Читать", R.drawable.ic_read_24)
    object Rename : Tool("Переименовать", R.drawable.ic_edit_24)
    object Delete : Tool("Удалить", R.drawable.ic_delete_24)
}


