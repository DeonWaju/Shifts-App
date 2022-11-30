package com.example.shiftstestapplication.data.repository

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.db.ShiftsDatabase
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    private val shiftList = mutableListOf<Shift>()


    fun upsert(shifts: ShiftItems) {
        db.shiftsDao().upsert(shifts)
    }

    fun delete(shifts: ShiftItems) {
        db.shiftsDao().delete(shifts)
    }

    fun isExists(): Boolean {
        return db.shiftsDao().isExists()
    }

    fun getShifts(): List<Shift> {
        shiftList.clear()
        getData(response)
        return shiftList
    }

    private fun getData(response: ShiftsList) {
        response.shifts.map { shift ->
            shiftList.addAll(listOf(shift))
            db.shiftsDao().deleteAll()
            db.shiftsDao().upsert(
                item = ShiftItems(
                    name = shift.name,
                    role = shift.role,
                    startDate = shift.start_date,
                    endDate = shift.end_date,
                    color = shift.color,
                )
            )
        }
    }


}