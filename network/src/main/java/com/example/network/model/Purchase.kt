package com.example.network.model

import com.example.database.GoodsEntity

sealed class PurchaseHistoryInfo {
    data class PurchaseHistoryDate(
        val date: String
    ): PurchaseHistoryInfo()

    data class PurchaseTicketHistory(
        val reservationIds: String,
        val seatNumbers: String,
        val date: String,
        val time: String,
        val leftTeam: String,
        val rightTeam: String
    ): PurchaseHistoryInfo()

    data class PurchaseGoodsHistory(
        val amount: Int,
        val category: String,
        val name: String,
        val price: Int,
        val image: String,
        val date: String
    ): PurchaseHistoryInfo()

    companion object {
        fun ticketHistoryListMapper(list: List<PurchaseTicketHistory>): List<PurchaseHistoryInfo> {
            val newList = mutableListOf<PurchaseHistoryInfo>()
            list
                .groupBy { ticket -> ticket.date + " " + ticket.time }
                .mapKeys { (key, value) ->
                    newList.add(PurchaseHistoryDate(key))
                    newList.addAll(value)
                }
            return newList
        }

        fun goodsHistoryListMapper(list: List<PurchaseGoodsHistory>): List<PurchaseHistoryInfo> {
            val newList = mutableListOf<PurchaseHistoryInfo>()
            list
                .groupBy { ticket -> ticket.date }
                .mapKeys { (key, value) ->
                    newList.add(PurchaseHistoryDate(key))
                    newList.addAll(value)
                }
            return newList
        }
    }
}

data class Goods(
    val goodsId: Int,
    val category: String,
    val name: String,
    val price: Int,
    val url: String
)

data class GoodsDetail(
    val goodsId: Int,
    val category: String,
    val name: String,
    val price: Int,
    val imageList: List<String>
) {
    fun toEntity(amount: Int) = GoodsEntity(
        index = 0,
        category = category,
        name = name,
        price = price,
        amount = amount,
        image = imageList.getOrElse(0, defaultValue = { "" }),
        goodsId = goodsId
    )
    companion object {
        fun init() = GoodsDetail(
            category = "",
            name = "",
            price = 0,
            imageList = listOf(),
            goodsId = 0
        )
    }
}

data class ProductPurchase(
    val userId: Int,
    val goodsId: Int,
    val amount: Int,
    val productsPrice: Int
)

data class PurchaseInfo(
    val nickname: String,
    val mobile: String,
    val address: String,
    val cash: Int
) {
    companion object {
        fun init() = PurchaseInfo(
            "",
            "",
            "",
            0
        )
    }

    fun checkValidation() = when {
        nickname.isEmpty() || mobile.isEmpty() || address.isEmpty() ->
            false
        else -> true
    }
}