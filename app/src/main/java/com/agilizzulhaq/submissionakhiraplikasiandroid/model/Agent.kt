package com.agilizzulhaq.submissionakhiraplikasiandroid.model

data class Agent(
    val id: Int,
    val image: Int,
    val name: String,
    val role: String,
    val origin: String,
    val gender: String,
    val biography: String,
    val personality: String,
    val appearance: String,
    var isFavorite: Boolean = false
)