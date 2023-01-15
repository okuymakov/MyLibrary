package com.kuymakov.mylibrary.di

import com.kuymakov.mylibrary.data.local.MyBooksDataSource
import com.kuymakov.mylibrary.data.local.MyBooksDataSourceImpl
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