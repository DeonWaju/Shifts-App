package com.example.shiftstestapplication.domain.usecase

import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import com.example.shiftstestapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
interface ShiftUsecase {
    suspend fun upsert(item: ShiftItems)

    suspend fun delete(item: ShiftItems)

    suspend fun getShifts(): List<ShiftItems>
}