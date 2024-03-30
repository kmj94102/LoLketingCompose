package com.example.network.model

data class ChattingListParam(
    val date: String
)

data class ChattingRoomInfo(
    val gameId: Int,
    val gameTime: String,
    val leftTeam: String,
    val rightTeam: String
)

enum class Team(val teamName: String, val image: String) {
    T1("T1", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_t1.png?alt=media&token=d9f4dea2-0813-4221-a902-951469621b10"),
    DRX("DRX", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_drx.png?alt=media&token=2e416fe7-f24f-4dac-909f-ad5a62b46d41"),
    Hanwha("Hanwha Life Esports", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_hanwha.png?alt=media&token=d5902b3e-9c7f-4c74-818a-d29dd49193db"),
    GenG("Gen.G Esports", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_geng.png?alt=media&token=0c6e94fe-c358-4f1d-ac99-631d7e194b35"),
    OK("OKSavingsBank BRION", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_ok.png?alt=media&token=940ed070-7815-41ec-9ac9-ed9fcd337ea9"),
    KIA("Dplus KIA", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_kia.png?alt=media&token=df81ad5d-a513-4e09-bbf5-c529a5949b4f"),
    MVP("MVP", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_mvp.png?alt=media&token=0e93e37f-ebb1-47e4-b361-97ed7ad2b7cf"),
    KT("kt Rolster", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_kt.png?alt=media&token=1e9eb398-ac2a-4fbf-9ec2-3ef9e815970d"),
    Griffin("Griffin", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_griffin.png?alt=media&token=f53c4035-ef32-4c48-aa54-250bba1f8369"),
    CJ("CJ Entus", "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/logo%2Fimg_cj.png?alt=media&token=d50d3e36-6a7b-4358-b45c-9165b29d33e8");

    companion object {
        fun getTeamImage(name: String) =
            values().firstOrNull { it.teamName == name }?.image ?: T1.image
    }
}