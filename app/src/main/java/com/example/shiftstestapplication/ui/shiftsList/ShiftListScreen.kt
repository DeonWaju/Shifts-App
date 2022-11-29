package com.example.shiftstestapplication.ui.shiftsList

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

/**
 * Created by Gideon Olarewaju on 29/11/2022.
 */


@Composable
fun ShiftsListScreen(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel()
) {
    val getData = viewModel.getLists()

    Column {
        ShiftsList(navController)
    }
}

@Composable
fun ShiftsList(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel()
){
    val shiftList by remember { viewModel.shiftList }

}