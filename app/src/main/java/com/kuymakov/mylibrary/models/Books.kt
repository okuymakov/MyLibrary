package com.kuymakov.mylibrary.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Books(
    @SerialName("items")
    val books: List<Book>
)

@Serializable
data class Book(
    val id: String,
    @SerialName("volumeInfo")
    val bookInfo: BookInfo,
)

@Serializable
data class BookInfo(
    val title: String,
    val authors: List<String>? = null,
    val description: String? = null,
    @SerialName("imageLinks")
    val imageLink: ImageLink? = null,
)

@Serializable
data class ImageLink(
    @SerialName("thumbnail")
    val url: String
)
