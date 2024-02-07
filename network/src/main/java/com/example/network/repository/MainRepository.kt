package com.example.network.repository

import com.example.network.model.MyInfo
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun fetchMyInfo(): Flow<MyInfo>

}