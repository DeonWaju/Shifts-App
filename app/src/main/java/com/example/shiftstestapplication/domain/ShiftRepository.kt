package com.example.shiftstestapplication.domain

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
interface ShiftRepository {
    suspend fun getShifts()
}