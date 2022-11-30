package com.example.shiftstestapplication.ui.shiftsList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.domain.usecase.FilterShiftsUseCase
import com.example.shiftstestapplication.domain.usecase.ShiftRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

@HiltViewModel
class ShiftsListViewModel @Inject constructor(
    private val filterShiftsUseCase: FilterShiftsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        getLists()
    }

    private val _uiState = MutableStateFlow(emptyList<Shift>())
    val uiState: StateFlow<List<Shift>> = _uiState.asStateFlow()

//    var shiftList = mutableStateOf<List<Shift>>(listOf())
//    var cachedShiftList = listOf<Shift>()
//

    fun getLists() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = filterShiftsUseCase.getShifts()
        }
    }

}