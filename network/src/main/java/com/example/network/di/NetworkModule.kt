package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.client.AddressClient
import com.example.network.client.MainClient
import com.example.network.client.PurchaseClient
import com.example.network.service.AddressService
import com.example.network.service.MainService
import com.example.network.service.PurchaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("main")
    fun provideMainRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://port-0-mj-api-e9btb72blgnd5rgr.sel3.cloudtype.app/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMainService(
        @Named("main") retrofit: Retrofit
    ): MainService =
        retrofit.create(MainService::class.java)

    @Provides
    @Singleton
    fun provideMainClient(
        service: MainService,
    ): MainClient = MainClient(service)

    @Provides
    @Singleton
    fun providePurchaseService(
        @Named("main") retrofit: Retrofit
    ): PurchaseService =
        retrofit.create(PurchaseService::class.java)

    @Provides
    @Singleton
    fun providePurchaseClient(
        service: PurchaseService,
    ): PurchaseClient = PurchaseClient(service)

    @Provides
    @Singleton
    @Named("address")
    fun provideAddressRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.juso.go.kr/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideAddressService(@Named("address") retrofit: Retrofit) : AddressService =
        retrofit.create(AddressService::class.java)

    @Provides
    @Singleton
    fun provideAddressClient(addressService: AddressService) : AddressClient =
        AddressClient(addressService)

}