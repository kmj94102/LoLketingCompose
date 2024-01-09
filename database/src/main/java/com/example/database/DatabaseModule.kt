package com.example.database

import android.app.Application
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): LoLketingDatabase = Room
        .databaseBuilder(application, LoLketingDatabase::class.java, "lolketing.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideAuthDao(
        database: LoLketingDatabase
    ): AuthDao = database.authDao()

}

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseRepositoryModule {
    @Binds
    fun bindDatabaseRepository(
        databaseRepositoryImpl: DatabaseRepositoryImpl
    ): DatabaseRepository
}