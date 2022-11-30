package com.example.shiftstestapplication.domain.implementation

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import com.example.shiftstestapplication.domain.usecase.ShiftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

class ShiftsUsecaseImpl @Inject constructor(
    private val repository: ShiftsRepository
) : ShiftRepository {

    private val response = Json.decodeFromString<ShiftsList>(shiftsJson)
    private val shiftList = mutableListOf<Shift>()

    override suspend fun upsert(item: ShiftItems) {
        repository.upsert(item)
    }

    override suspend fun delete(item: ShiftItems) {
        repository.delete(item)
    }

    override suspend fun getShifts(): Flow<List<Shift>> = flow{
        shiftList.clear()
        getData(response)
        emit(shiftList)
    }

    private fun getData(response: ShiftsList) {
        response.shifts.map {
            shiftList.addAll(listOf(it))
        }
    }
}