package com.example.shiftstestapplication.domain.implementation

import com.example.shiftstestapplication.data.dataSource.shiftsJson
import com.example.shiftstestapplication.data.db.ShiftsDatabase
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.repository.ShiftsRepository
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.data.responses.ShiftsList
import com.example.shiftstestapplication.domain.usecase.ShiftUsecase
import com.example.shiftstestapplication.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

@ActivityScoped
class ShiftsUsecaseImpl @Inject constructor(
    private val repository: ShiftsRepository
) : ShiftUsecase {

    override suspend fun upsert(item: ShiftItems) {
        repository.upsert(item)
    }

    override suspend fun delete(item: ShiftItems) {
        repository.delete(item)
    }

    override suspend fun getShifts(): Flow<List<Shift>> = flow {
        repository.getShifts()
    }

}