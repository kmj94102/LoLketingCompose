package com.example.network.client

import com.example.network.model.IdParam
import com.example.network.service.MainService
import com.example.network.util.result
import javax.inject.Inject

class MainClient @Inject constructor(
    private val service: MainService
) {

    suspend fun fetchMyInfo(id: String) = runCatching {
        service.fetchMyInfo(IdParam(id)).result()
    }
}