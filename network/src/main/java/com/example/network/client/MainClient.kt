package com.example.network.client

import com.example.network.model.StringIdParam
import com.example.network.model.ModifyInfo
import com.example.network.model.RouletteCouponUpdateItem
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import com.example.network.model.IntIdParam
import com.example.network.service.MainService
import com.example.network.util.result
import javax.inject.Inject

class MainClient @Inject constructor(
    private val service: MainService
) {
    suspend fun fetchMyInfo(id: String) = runCatching {
        service.fetchMyInfo(StringIdParam(id)).result()
    }

    suspend fun fetchCashInfo(id: String) = runCatching {
        service.fetchCashInfo(StringIdParam(id)).result()
    }

    suspend fun updateCashCharging(item: UpdateCashItem) = runCatching {
        service.updateCashCharging(item).result()
    }

    suspend fun fetchCouponList(id: String) = runCatching {
        service.fetchCouponList(StringIdParam(id)).result()
    }

    suspend fun updateUsingCoupon(item: UpdateCouponItem) = runCatching {
        service.updateUsingCoupon(item).result()
    }

    suspend fun updateMyInfo(item: ModifyInfo) = runCatching {
        service.updateMyInfo(item).result()
    }

    suspend fun fetchNewUserCoupon(item: StringIdParam) = runCatching {
        service.fetchNewUserCoupon(item).result()
    }

    suspend fun insertNewUserCoupon(item: IntIdParam) = runCatching {
        service.insertNewUserCoupon(item).result()
    }

    suspend fun insertRouletteCoupon(item:RouletteCouponUpdateItem) = runCatching {
        service.insertRouletteCoupon(item).result()
    }

    suspend fun fetchRouletteCount(item: IntIdParam) = runCatching {
        service.fetchRouletteCount(item).result()
    }

    suspend fun fetchTicketHistory(item: IntIdParam) = runCatching {
        service.fetchTicketHistory(item).result()
    }

    suspend fun fetchGoodsHistory(item: IntIdParam) = runCatching {
        service.fetchGoodsHistory(item).result()
    }
}