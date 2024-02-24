package com.example.network.model

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
    }
}
