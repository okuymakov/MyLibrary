package com.kuymakov.mylibrary.di

import com.kuymakov.mylibrary.data.remote.BooksService
import com.kuymakov.mylibrary.data.repositories.BooksRepository
import com.kuymakov.mylibrary.data.repositories.BooksRepositoryImpl
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