package com.kuymakov.mylibrary.data.remote

import com.kuymakov.mylibrary.models.Books
import retrofit2.http.GET
import retrofit2.http.Query


interface BooksService {

    @GET("volumes?key=AIzaSyDIugDMe_pua9S3SJjrioyqpCrd7VT-Cr8")
    suspend fun getBooks(@Query("q") category: String) : Books
}