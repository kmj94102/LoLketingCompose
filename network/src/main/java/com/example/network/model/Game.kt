package com.example.network.model

data class Game(
    val gameId: Int,
    val gameDate: String,
    val leftTeam: String,
    val rightTeam: String
) {
    companion object {
        fun mockData() = Game(
            gameId = 1,
            gameDate = "24.01.01 17:00",
            leftTeam = "kt Rolster",
            rightTeam = "GRIFFIN"
        )
    }
}

data class TicketInfoParam(
    val gameId: Int,
    val userId: Int
)

data class Seat(
    val number: String,
    val isReserved: Boolean,
    val isSelected: Boolean = false
)

data class ReservationInfo(
    val userId: Int,
    val gameId: Int,
    val date: String,
    val gameTitle: String,
    val cash: Int,
    val reservedSeats: List<String>,
    val selectedSeats: List<String>
) {
    fun getSeatList(): List<Seat> {
        val resultList = mutableListOf<Seat>()
        ('A'..'H').forEach { line ->
            (1..8).forEach { num ->
                val number = "$line$num"
                resultList.add(
                    Seat(
                        number = number,
                        isReserved = reservedSeats.contains(number),
                        isSelected = selectedSeats.contains(number)
                    )
                )
            }
        }
        return resultList
    }

    fun setItem(item: ReservationItem): ReservationInfo{
        val selectedSeats = removeDuplicates(item.reservedSeats)
        return ReservationInfo(
            userId = item.userId,
            gameId = item.gameId,
            date = item.date,
            gameTitle = item.gameTitle,
            cash = item.cash,
            reservedSeats = item.reservedSeats,
            selectedSeats = selectedSeats
        )
    }

    private fun removeDuplicates(newList: List<String>): List<String> {
        val resultList = selectedSeats.toMutableList()
        for (item in newList) {
            resultList.remove(item)
        }
        return resultList
    }

    companion object {
        fun init() = ReservationInfo(
            userId = 0,
            gameId = 0,
            date = "",
            gameTitle = "",
            cash = 0,
            reservedSeats = listOf(),
            selectedSeats = listOf()
        )
    }
}

data class ReservationItem(
    val userId: Int,
    val gameId: Int,
    val date: String,
    val gameTitle: String,
    val cash: Int,
    val reservedSeats: List<String>
)

data class ReservationTicketItem(
    val gameId: Int,
    val userId: Int,
    val count: Int,
    val seatNumber: String
) {
    fun checkValidation() = when {
        gameId == 0 -> throw Exception("경기 정보가 없습니다.")
        userId == 0 -> throw Exception("유저 정보가 없습니다.")
        seatNumber.isEmpty() -> throw Exception("좌석을 선택해 주세요.")
        seatNumber.split(",").size != count -> throw Exception("선택한 인원수를 확인해 주세요.")
        else -> true
    }
}

data class TicketIdParam(
    val idList: List<Int>
)

data class TicketInfo(
    val leftTeam: String,
    val rightTeam: String,
    val time: String,
    val seats: String
) {
    fun gameTitle() = "$leftTeam VS $rightTeam"

    fun getSeatsInfo() = "A홀 $seats"
}