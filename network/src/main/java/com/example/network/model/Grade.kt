package com.example.network.model

enum class Grade(
    val koreanName: String,
    val code: String,
    val maxPoint: Int,
    val image: String
) {
    BRONZE(
        "브론즈",
        "USER001",
        3_000,
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/bronze.png?alt=media&token=8826d27f-be2d-423e-a4cb-37b6587d914c"
    ),
    SILVER(
        "실버",
        "USER002",
        30_000,
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/silver.png?alt=media&token=083c09ba-b311-45de-ba9c-852f2b73afee"
    ),
    GOLD(
        "골드",
        "USER003",
        300_000,
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/gold.png?alt=media&token=42ba695d-2f81-43fa-8e6b-c44bda6c827a"
    ),
    PLATINUM(
        "플래티넘",
        "USER004",
        300_000,
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/platinum.png?alt=media&token=e87bbc24-e3dc-4d9b-8695-96dce9e4979e"
    ),
    MASTER(
        "마스터",
        "USER005",
        300_000,
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/master.png?alt=media&token=72ebd38e-23b2-4462-89ab-598c85d06760"
    );

    companion object {
        fun getImage(code: String) =
            values().firstOrNull { it.code == code }?.image ?: BRONZE.image

        fun getMaxPoint(code: String) =
            values().firstOrNull { it.code == code }?.maxPoint ?: BRONZE.maxPoint

        fun getKoreanName(code: String) =
            values().firstOrNull { it.code == code }?.koreanName ?: BRONZE.koreanName
    }
}