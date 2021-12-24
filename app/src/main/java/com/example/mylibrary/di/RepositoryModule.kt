package com.example.mylibrary.di

import com.example.mylibrary.data.remote.BooksService
import com.example.mylibrary.data.repositories.BooksRepository
import com.example.mylibrary.data.repositories.BooksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideRepo(repo: BooksRepositoryImpl): BooksRepository
}