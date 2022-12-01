package com.example.shiftstestapplication.domain.implementation

import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

class ShiftsUsecaseImpl @Inject constructor(
    private val shiftsRepository: ShiftsRepository
    ) : ShiftListUsecase {
    override suspend fun getShifts() = shiftsRepository.getShifts()
}