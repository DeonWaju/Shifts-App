package com.example.shiftstestapplication.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class Shift(
    val color: String,
    val end_date: String,
    val name: String,
    val role: String,
    val start_date: String
)