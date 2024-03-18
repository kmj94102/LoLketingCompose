package com.example.network.repository

import com.example.network.model.Coupon
import com.example.network.model.ModifyInfo
import com.example.network.model.MyCash
import com.example.network.model.MyInfo
import com.example.network.model.PurchaseHistoryInfo
import com.example.network.model.RouletteCount
import com.example.network.model.UpdateCouponItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun fetchMyInfo(): Flow<MyInfo>

    fun fetchModifyInfo(): Flow<ModifyInfo>
    
    fun fetchCashInfo(): Flow<MyCash>

    fun updateCashCharging(cash: Int): Flow<MyInfo>

    fun fetchCouponList(id: String): Flow<List<Coupon>>

    fun updateUsingCoupon(couponId: Int): Flow<MyInfo>

    fun updateMyInfo(item: ModifyInfo): Flow<Unit>

    fun fetchNewUserCoupon(): Flow<Boolean>

    fun insertNewUserCoupon(): Flow<Unit>

    suspend fun insertRouletteCoupon(rp: Int): Result<RouletteCount>

    fun fetchRouletteCount(): Flow<RouletteCount>

    fun fetchTicketHistory(): Flow<List<PurchaseHistoryInfo>>

    fun fetchGoodsHistory(): Flow<List<PurchaseHistoryInfo>>

}