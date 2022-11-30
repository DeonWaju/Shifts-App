package com.example.shiftstestapplication.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class Shift(
    val name: String,
    val color: String,
    val role: String,
    val start_date: String,
    val end_date: String
)