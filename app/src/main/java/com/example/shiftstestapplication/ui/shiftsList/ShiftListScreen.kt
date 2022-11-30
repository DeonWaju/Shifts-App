package com.example.shiftstestapplication.ui.shiftsList

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shiftstestapplication.data.responses.Shift
import kotlinx.coroutines.flow.collect

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */


@Composable
fun ShiftsListScreen(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel()
) {
    val shiftState = viewModel.uiState

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            ShiftScreenAppbar(navController = navController)
            Spacer(modifier = Modifier.height(20.dp))
            ShiftListView(navController, shiftState.shift)
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

@Composable
fun ShiftListView(
    navController: NavController,
    shiftList: List<Shift>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .border(width = 4.dp, color = Color.Black, shape = RectangleShape)
    ) {
        items(shiftList) {
            ShiftItemView(it)
        }
    }
}

@Composable
fun ShiftItemView(
    shift: Shift
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) {
        val (name, time) = createRefs()

        Text(
            text = "${shift.name} " + "(" + shift.role + ")",
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "${shift.start_date}",
            textAlign = TextAlign.End,
            modifier = Modifier.constrainAs(time) {
                end.linkTo(parent.end)
            }
        )
    }
    Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Black)

}