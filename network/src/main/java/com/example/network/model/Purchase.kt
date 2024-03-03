package com.example.network.model

import com.example.database.GoodsEntity

sealed class PurchaseHistoryInfo {
    data class PurchaseHistoryDate(
        val date: String
    ): PurchaseHistoryInfo()

    data class PurchaseTicketHistory(
        val reservationIds: String,
        val date: String,
        val time: String,
        val leftTeam: String,
        val rightTeam: String
    ): PurchaseHistoryInfo() {
        fun toGame() = Game(
            gameId = 0,
            gameDate = "$date $time",
            leftTeam = leftTeam,
            rightTeam = rightTeam
        )
    }

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
                .groupBy { ticket -> ticket.date }
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
        image = imageList.getOrElse(0, defaultValue = { "" })
    )
    companion object {
        fun init() = GoodsDetail(
            category = "",
            name = "",
            price = 0,
            imageList = listOf()
        )
    }
}

data class ProductPurchase(
    val userId: Int,
    val goodsId: Int,
    val amount: Int
)