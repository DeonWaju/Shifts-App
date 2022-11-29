package com.example.shiftstestapplication.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ShiftsList(
    val shifts: List<Shift>
)