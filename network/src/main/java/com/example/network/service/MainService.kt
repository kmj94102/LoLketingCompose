package com.example.network.service

import com.example.network.model.Coupon
import com.example.network.model.IdParam
import com.example.network.model.ModifyInfo
import com.example.network.model.MyCash
import com.example.network.model.MyInfo
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {
    @POST("/user/select/myInfo")
    suspend fun fetchMyInfo(@Body item: IdParam): Response<MyInfo>

    @POST("/user/select/cash")
    suspend fun fetchCashInfo(@Body item: IdParam): Response<MyCash>

    @POST("/user/update/charging")
    suspend fun updateCashCharging(@Body item: UpdateCashItem): Response<MyInfo>

    @POST("/user/select/couponList")
    suspend fun fetchCouponList(@Body item: IdParam): Response<List<Coupon>>

    @POST("/user/update/usingCoupon")
    suspend fun updateUsingCoupon(@Body item: UpdateCouponItem): Response<MyInfo>

    @POST("/user/update/myInfo")
    suspend fun updateMyInfo(@Body item: ModifyInfo): Response<Unit>

}