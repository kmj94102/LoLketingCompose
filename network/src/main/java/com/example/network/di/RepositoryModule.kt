package com.example.network.di

import com.example.network.repository.AddressRepository
import com.example.network.repository.AddressRepositoryImpl
import com.example.network.repository.MainRepository
import com.example.network.repository.MainRepositoryImpl
import com.example.network.repository.NewsRepository
import com.example.network.repository.NewsRepositoryImpl
import com.example.network.repository.PurchaseRepository
import com.example.network.repository.PurchaseRepositoryImpl
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

    @Binds
    fun bindAddressRepository(
        repository: AddressRepositoryImpl
    ): AddressRepository

    @Binds
    fun bindPurchaseRepository(
        repository: PurchaseRepositoryImpl
    ): PurchaseRepository

    @Binds
    fun bindNewsRepository(
        repository: NewsRepositoryImpl
    ): NewsRepository

}