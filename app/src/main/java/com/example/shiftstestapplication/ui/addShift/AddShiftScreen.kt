package com.example.shiftstestapplication.ui.addShift

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Gideon Olarewaju on 30/11/2022.
 */


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddShiftScreen(
    navController: NavController,
    viewModel: AddShiftViewModel = hiltViewModel()
) {
    val shiftState = viewModel.uiState
    val shift = shiftState.shift
    val employeeList = shift.map { it.name.capitalize(Locale.ROOT) }.distinct().toList()
    val rolesList = shift.map { it.role.capitalize(Locale.ROOT) }.distinct().toList()
    val colorsList = shift.map { it.color.capitalize(Locale.ROOT) }.distinct().toList()

//    val addShift = viewModel.upsert()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            AddShiftScreenAppbar(navController = navController)

            Box(modifier = Modifier.padding(16.dp)) {
                Column {

                    DateTimePicker()
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = employeeList,
                        label = "Employee"
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = rolesList,
                        label = "Role"
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DropDownMenu(
                        optionList = colorsList,
                        label = "Color"
                    )
                }
            }
        }
    }
}

@Composable
fun AddShiftScreenAppbar(
    navController: NavController,
    context: Context = LocalContext.current.applicationContext
) {
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
//                do something
            }) {
                Text(text = "Save")
            }
        }
    )
}

@Composable
fun DropDownMenu(optionList: List<String>, label: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable { expanded = !expanded },
            label = { Text(label) },
            trailingIcon = {
                Icon(icon, "Drop Down Icon",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
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
                .clickable(onClick = onClick),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePicker(
    viewModel: AddShiftViewModel = hiltViewModel()
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
    var start_date by remember {
        mutableStateOf(viewModel.startDate)
    }
    start_date = formattedDateStart
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
            TextFieldValue(formattedDateStart)
            viewModel.editTitle(formattedDateStart)
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
        onValueChange = { TextFieldValue(formattedDateEnd) },
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
        }
    }
}
