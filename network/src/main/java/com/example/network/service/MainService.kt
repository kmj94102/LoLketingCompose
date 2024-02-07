package com.example.network.service

import com.example.network.model.IdParam
import com.example.network.model.MyInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {
    @POST("/user/select/myInfo")
    suspend fun fetchMyInfo(@Body item: IdParam): Response<MyInfo>
}