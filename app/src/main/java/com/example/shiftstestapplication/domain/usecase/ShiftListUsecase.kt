package com.example.shiftstestapplication.domain.usecase

import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import kotlinx.coroutines.flow.Flow

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
interface ShiftListUsecase {
    suspend fun upsert(item: ShiftItems)

    suspend fun delete(item: ShiftItems)

    suspend fun isExists(): Flow<Boolean>

    suspend fun getShifts(): Flow<List<ShiftItems>>
}