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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

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

    private val _title by lazy { MutableStateFlow<String>("") }
    val title: StateFlow<String> by lazy { _title.asStateFlow() }

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

    fun editTitle(title: String) {
        _title.value = title
    }

    fun upsert() {
        viewModelScope.launch{
            var shift =  ShiftItems(
                Random.nextInt(12..1000),
                name = _title.value,
                role = role,
                startDate = startDate,
                endDate = endDate,
                color = color,
            )
            addShiftToListUsecase.add(shift)
        }
    }
}
