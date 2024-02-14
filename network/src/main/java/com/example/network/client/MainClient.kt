package com.example.network.client

import com.example.network.model.IdParam
import com.example.network.model.ModifyInfo
import com.example.network.model.RouletteCouponUpdateItem
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import com.example.network.model.UserIdParam
import com.example.network.service.MainService
import com.example.network.util.result
import javax.inject.Inject

class MainClient @Inject constructor(
    private val service: MainService
) {
    suspend fun fetchMyInfo(id: String) = runCatching {
        service.fetchMyInfo(IdParam(id)).result()
    }

    suspend fun fetchCashInfo(id: String) = runCatching {
        service.fetchCashInfo(IdParam(id)).result()
    }

    suspend fun updateCashCharging(item: UpdateCashItem) = runCatching {
        service.updateCashCharging(item).result()
    }

    suspend fun fetchCouponList(id: String) = runCatching {
        service.fetchCouponList(IdParam(id)).result()
    }

    suspend fun updateUsingCoupon(item: UpdateCouponItem) = runCatching {
        service.updateUsingCoupon(item).result()
    }

    suspend fun updateMyInfo(item: ModifyInfo) = runCatching {
        service.updateMyInfo(item).result()
    }

    suspend fun fetchNewUserCoupon(item: IdParam) = runCatching {
        service.fetchNewUserCoupon(item).result()
    }

    suspend fun insertNewUserCoupon(item: UserIdParam) = runCatching {
        service.insertNewUserCoupon(item).result()
    }

    suspend fun insertRouletteCoupon(item:RouletteCouponUpdateItem) = runCatching {
        service.insertRouletteCoupon(item).result()
    }

    suspend fun fetchRouletteCount(item: UserIdParam) = runCatching {
        service.fetchRouletteCount(item).result()
    }
}