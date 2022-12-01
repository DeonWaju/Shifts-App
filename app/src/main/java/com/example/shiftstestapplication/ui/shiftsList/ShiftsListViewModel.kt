package com.example.shiftstestapplication.ui.shiftsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

data class ShiftUiState(
    val shift: MutableList<Shift> = mutableListOf(),
)

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val shiftListUsecase: ShiftListUsecase,
    private val addShiftToListUsecase: AddShiftToListUsecase
) : ViewModel() {

    private val _uiState by lazy { mutableStateOf(ShiftUiState()) }
    var uiState = _uiState.value
        private set

    init {
        getShifts()
    }

    fun addShift(singleShift: Shift) {
        viewModelScope.launch {
            addShiftToListUsecase.invoke(singleShift)
        }
    }

    private fun getShifts() {
        viewModelScope.launch {
            shiftListUsecase.getShifts().collect { shiftList ->
                uiState = uiState.copy(
                    shift = shiftList
                )
            }
        }
    }
}
