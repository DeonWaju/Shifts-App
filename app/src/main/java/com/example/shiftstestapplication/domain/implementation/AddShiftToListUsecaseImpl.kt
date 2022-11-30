package com.example.shiftstestapplication.domain.implementation

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.db.ShiftsDatabase
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

class AddShiftToListUsecaseImpl @Inject constructor(
    private val shiftsRepository: ShiftsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AddShiftToListUsecase {

    override suspend fun add(item: ShiftItems) {
        return shiftsRepository.upsert(item)
    }

    override suspend fun getShifts(): Flow<List<Shift>> = flow {
        val shiftList = shiftsRepository.getShifts()
        emit(shiftList)
    }.flowOn(ioDispatcher)

}