package com.example.shiftstestapplication.ui.addShift

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstestapplication.domain.usecase.AddShiftToListUsecase
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import com.example.shiftstestapplication.ui.shiftsList.ShiftUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 30/11/2022.
 */

@HiltViewModel
class AddShiftViewModel @Inject constructor(
    private val addShiftToListUsecase: AddShiftToListUsecase
) : ViewModel() {

    var uiState by mutableStateOf(ShiftUiState(emptyList()))
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

}
