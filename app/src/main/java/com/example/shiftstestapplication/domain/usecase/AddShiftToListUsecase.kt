package com.example.shiftstestapplication.domain.usecase

import com.example.shiftstestapplication.data.responses.Shift

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */
interface AddShiftToListUsecase {
    suspend operator fun invoke(item: Shift)
}