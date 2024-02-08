package com.example.network.repository

import com.example.network.model.Coupon
import com.example.network.model.ModifyInfo
import com.example.network.model.MyCash
import com.example.network.model.MyInfo
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun fetchMyInfo(): Flow<MyInfo>

    fun fetchModifyInfo(): Flow<ModifyInfo>
    
    suspend fun fetchCashInfo(): Flow<MyCash>

    suspend fun updateCashCharging(item: UpdateCashItem): Flow<MyInfo>

    suspend fun fetchCouponList(id: String): Flow<List<Coupon>>

    suspend fun updateUsingCoupon(item: UpdateCouponItem): Flow<MyInfo>

    suspend fun updateMyInfo(item: ModifyInfo): Flow<Unit>

}