package com.example.auth.di

import com.example.auth.repository.AuthRepository
import com.example.auth.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun bindAuthRepository(
        repository: AuthRepositoryImpl
    ): AuthRepository

}