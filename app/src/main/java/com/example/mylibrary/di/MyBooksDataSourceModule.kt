package com.example.mylibrary.di

import com.example.mylibrary.data.local.MyBooksDataSource
import com.example.mylibrary.data.local.MyBooksDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MyBooksDataSourceModule {
    @Binds
    fun provide(dataSource: MyBooksDataSourceImpl): MyBooksDataSource
}