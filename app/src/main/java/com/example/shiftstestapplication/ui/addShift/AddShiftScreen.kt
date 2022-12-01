package com.example.shiftstestapplication.ui.addShift

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shiftstestapplication.R
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.ui.shiftsList.ShiftsListViewModel
import com.example.shiftstestapplication.utils.showToast
import com.example.shiftstestapplication.utils.toDateTime
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

/**
 * Created by Gideon Olarewaju on 30/11/2022.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddShiftScreen(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val shiftState = viewModel.uiState

    val existingList = shiftState.shift
    val employeeList = existingList.map { it.name.capitalize(Locale.ROOT) }.distinct().toList()
    val rolesList = existingList.map { it.role.capitalize(Locale.ROOT) }.distinct().toList()
    val colorsList = existingList.map { it.color.capitalize(Locale.ROOT) }.distinct().toList()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "Create A Shift",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 4.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    Button(onClick = {
                        if (name.isNotEmpty() && name.isNotEmpty() && name.isNotEmpty() && name.isNotEmpty() && name.isNotEmpty()) {
                            val shift = Shift(
                                name = name,
                                role = role,
                                color = color.lowercase(Locale.ROOT),
                                start_date = startDate,
                                end_date = endDate
                            )
                            viewModel.addShift(shift)
                            navController.popBackStack()
                        }
                        else{
                            showToast(context, "Please ensure that all fields have been selected.")
                        }
                    }) {
                        Text(text = "Save")
                    }
                }
            )

            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    DateTimePicker(
                        pickedStartDate = { startDate = it },
                        pickedEndDate = { endDate = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = employeeList,
                        labelHint = "Employee",
                        name = { name = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = rolesList,
                        labelHint = "Role",
                        role = { role = it }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = colorsList,
                        labelHint = "Color",
                        color = { color = it }
                    )
                }
            }
       }


    }
}

@Composable
fun DropDownMenu(
    optionList: List<String>,
    labelHint: String,
    color: ((String) -> Unit)? = null,
    role: ((String) -> Unit)? = null,
    name: ((String) -> Unit)? = null) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable { expanded = !expanded },
            label = { Text(labelHint) },
            trailingIcon = {
                Icon(
                    icon,
                    "Drop Down Icon",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            when (labelHint) {
                "Employee" -> name?.invoke(selectedText)
                "Role" -> role?.invoke(selectedText)
                "Color" -> color?.invoke(selectedText)
            }
            optionList.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = !expanded
                }) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePicker(
    viewModel: ShiftsListViewModel = hiltViewModel(),
    pickedStartDate: ((String) -> Unit)? = null,
    pickedEndDate: ((String) -> Unit)? = null
) {
    var pickedDateStart by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedDateEnd by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDateStart by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDateStart)
        }
    }

    val formattedDateEnd by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDateEnd)
        }
    }

    val dateDialogStateStart = rememberMaterialDialogState()
    val dateDialogStateEnd = rememberMaterialDialogState()

    Spacer(modifier = Modifier.height(20.dp))
    ReadonlyTextField(
        value = TextFieldValue(formattedDateStart),
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {
            pickedStartDate?.invoke(it.text)
        },
        onClick = { dateDialogStateStart.show() },
        label = {
            Text(text = "Start Date")
        }
    )
    Spacer(modifier = Modifier.height(20.dp))
    ReadonlyTextField(
        value = TextFieldValue(formattedDateEnd),
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {

        },
        onClick = { dateDialogStateEnd.show() },
        label = {
            Text(text = "End Date")
        }
    )

    MaterialDialog(
        dialogState = dateDialogStateStart,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ) {
            pickedDateStart = it
            pickedStartDate?.invoke(formattedDateStart)
        }
    }
    MaterialDialog(
        dialogState = dateDialogStateEnd,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ) {
            pickedDateEnd = it
            pickedEndDate?.invoke(formattedDateEnd)
        }
    }
}
