package com.fardan.soulmatchalpha.model

data class MatchHistory(
    val timestamp: String = "",
    val type: String = "",
    val match_percentage: Int = 0,
    val your_name: String = "",
    val partner_name: String = "",
    val your_birthdate: String = "",
    val partner_birthdate: String = "",
    val your_zodiac: String = "",
    val partner_zodiac: String = ""
)

