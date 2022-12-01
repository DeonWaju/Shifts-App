package com.example.shiftstestapplication.data.repository

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

class ShiftsRepository {

    private val response = Json.decodeFromString<ShiftsList>(shiftsJson)

    private val listOfShifts: MutableList<Shift> = response.shifts.toMutableList()

    fun getShifts(): Flow<MutableList<Shift>> = flow {
        emit(listOfShifts)
    }

    fun insert(item: Shift) {
        listOfShifts.add(item)
    }
}
