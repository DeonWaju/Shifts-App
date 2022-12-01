package com.example.shiftstestapplication.ui.addShift

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import com.example.shiftstestapplication.ui.shiftsList.ShiftUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Gideon Olarewaju on 30/11/2022.
 */

@HiltViewModel
class AddShiftViewModel @Inject constructor(
    private val addShiftToListUsecase: AddShiftToListUsecase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var uiState by mutableStateOf(ShiftUiState(emptyList()))
        private set

    var name by mutableStateOf("")
        private set

    var role by mutableStateOf("")
        private set

    var color by mutableStateOf("")
        private set

    var startDate by mutableStateOf("")
        private set

    var endDate by mutableStateOf("")
        private set


    init {
        getShifts()
    }

    private fun getShifts() {
        viewModelScope.launch {
            addShiftToListUsecase.getShifts().collect { shifts ->
                uiState = uiState.copy(
                    shift = shifts
                )
            }
        }
    }

    fun upsert() {
        viewModelScope.launch{
            addShiftToListUsecase.add(
                ShiftItems(
                    Random.nextInt(),
                    name = name,
                    role = role,
                    startDate = startDate,
                    endDate = endDate,
                    color = color,
                )
            )
        }
    }
}
