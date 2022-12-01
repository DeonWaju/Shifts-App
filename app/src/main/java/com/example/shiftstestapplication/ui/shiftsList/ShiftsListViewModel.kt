package com.example.shiftstestapplication.ui.shiftsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstestapplication.data.db.entities.ShiftItems
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.domain.usecase.ShiftListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */


data class ShiftUiState(
    val shift: List<ShiftItems> = emptyList()
)

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val shiftListUsecase: ShiftListUsecase
) : ViewModel() {

    var uiState by mutableStateOf(ShiftUiState(emptyList()))
        private set

    init {
        getShifts()
    }

    fun upsert(item: ShiftItems) =
        viewModelScope.launch(Dispatchers.IO) {
            shiftListUsecase.upsert(item)
        }

    private fun getShifts() {
        viewModelScope.launch {
            shiftListUsecase.getShifts().collect { shifts ->
                uiState = uiState.copy(
                    shift = shifts
                )
            }
        }
    }

}