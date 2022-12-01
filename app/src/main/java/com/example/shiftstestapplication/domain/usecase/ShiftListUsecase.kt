package com.example.shiftstestapplication.domain.usecase

import com.example.shiftstestapplication.data.responses.Shift
import kotlinx.coroutines.flow.Flow

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
interface ShiftListUsecase {
    suspend fun getShifts(): Flow<MutableList<Shift>>
}