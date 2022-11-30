package com.example.shiftstestapplication.domain.usecase

import com.example.shiftstestapplication.data.responses.Shift
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterShiftsUseCase @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher,
    private val shiftRepository: ShiftRepository
) {
    suspend fun getShifts(): List<Shift> =
        withContext(defaultDispatcher) {
            var list = emptyList<Shift>()

            shiftRepository.getShifts().collect {
                list = it
            }

            list.minByOrNull {
                it.start_date
            }

            return@withContext list
        }
}