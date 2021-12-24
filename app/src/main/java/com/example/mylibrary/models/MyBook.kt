package com.example.mylibrary.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyBook(
    val title: String,
    val uri: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}