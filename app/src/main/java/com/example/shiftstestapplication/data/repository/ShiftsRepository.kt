package com.example.shiftstestapplication.data.repository

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.db.ShiftsDatabase
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

class ShiftsRepository @Inject constructor(
    private val db: ShiftsDatabase
) {
    private val response = Json.decodeFromString<ShiftsList>(shiftsJson)

    fun upsert(shifts: ShiftItems) {
        db.shiftsDao().upsert(shifts)
    }

    fun delete(shifts: ShiftItems) {
        db.shiftsDao().delete(shifts)
    }

    fun getShifts(): Flow<List<ShiftItems>> = flow {
        mapShifts()
        val shifts = db.shiftsDao().getAllShiftItems()
        emit(shifts)
    }

    private fun mapShifts() {
        response.shifts.map {
            db.shiftsDao().upsert(
                item = ShiftItems(
                    name = it.name,
                    role = it.role,
                    startDate = it.start_date,
                    endDate = it.end_date,
                    color = it.color,
                )
            )
        }
    }
}