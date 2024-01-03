package com.example.network.di

import com.example.network.repository.MainRepository
import com.example.network.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(
        repository: MainRepositoryImpl
    ): MainRepository

}