package com.kuymakov.mylibrary.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuymakov.mylibrary.data.remote.BooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder().baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(
                json.asConverterFactory(contentType)).build()
    }

    @Provides
    fun provideBooksService(retrofit: Retrofit): BooksService {
        return retrofit.create(BooksService::class.java)
    }
}