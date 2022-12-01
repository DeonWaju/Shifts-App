package com.example.shiftstestapplication.domain.implementation

import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
//
class AddShiftToListUsecaseImpl @Inject constructor(
    private val shiftsRepository: ShiftsRepository,
) : AddShiftToListUsecase {
    override suspend fun invoke(item: Shift) = shiftsRepository.insert(item)
}
