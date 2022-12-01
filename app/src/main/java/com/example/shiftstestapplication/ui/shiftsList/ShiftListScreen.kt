package com.example.shiftstestapplication.ui.shiftsList

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shiftstestapplication.data.responses.Shift
import com.example.shiftstestapplication.utils.convertToCustomFormat
import com.example.shiftstestapplication.utils.toDateStyle
import com.example.shiftstestapplication.utils.toFriendlyDate
import com.example.shiftstestapplication.utils.toFriendlyDateString


/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftsListScreen(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel()
) {
    val shiftState: ShiftUiState = remember { viewModel.uiState }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            ShiftScreenAppbar(navController = navController)
            Column (modifier = Modifier.padding(16.dp)){
                Spacer(modifier = Modifier.height(20.dp))
                ShiftListView(navController, shiftState.shift)
            }
        }
    }
}

@Composable
fun ShiftScreenAppbar(
    navController: NavController,
    context: Context = LocalContext.current.applicationContext
) {
    TopAppBar(
        title = {
            Text(
                text = "Coffee Co Shifts",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        actions = {
            Button(onClick = {
                navController.navigate("add_shift_screen") {
                    popUpTo("shift_list_screen")
                }
            }) {
                Text(text = "Add Shift")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftListView(
    navController: NavController,
    shiftList: List<Shift>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .border(width = 4.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        items(shiftList) {
            ShiftItemView(it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftItemView(
    shift: Shift
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (name, time) = createRefs()

        val color = when (shift.color) {
            "red" -> {
                Color.Red
            }
            "blue" -> {
                Color.Blue
            }
            else -> {
                Color.Green
            }
        }

        val date = "${shift.start_date}"
        val dateLength = if (date.length < 11){
           date
        } else{
            date.take(11)
        }

        Text(
            text = "${shift.name} " + "(" + shift.role + ")",
            textAlign = TextAlign.Start,
            color = color,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
            }
        )
        Text(
            text = dateLength,
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(time) {
                end.linkTo(parent.end)
            }
        )
    }
    Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)
}
