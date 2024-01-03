package com.example.network.repository

import com.example.network.client.MainClient
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val client: MainClient
) : MainRepository {
}