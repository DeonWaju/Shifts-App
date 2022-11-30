package com.example.shiftstestapplication.ui.shiftsList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
//    val exampleEntitiesFlowLifecycleAware = rememberFlow(viewModel.uiState)
//    val exampleEntities: List<ExampleEntity> by exampleEntitiesFlowLifecycleAware.collectAsState(initial = emptyList())
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))

            ShiftListView(navController)
        }
    }
}

@Composable
fun ShiftListView(
    navController: NavController,
    viewModel: ShiftsListViewModel = hiltViewModel()
){
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(0) {

        }
    }
}

@Composable
fun ShiftItemView(){


}