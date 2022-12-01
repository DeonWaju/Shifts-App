package com.example.shiftstestapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shiftstestapplication.ui.addShift.AddShiftScreen
import com.example.shiftstestapplication.ui.shiftsList.ShiftsListScreen
import com.example.shiftstestapplication.ui.shiftsList.ShiftsListViewModel
import com.example.shiftstestapplication.ui.theme.ShiftsTestApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftsTestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShiftHome()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShiftHome(
    viewModel: ShiftsListViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "shift_list_screen"
    ) {
        composable(route = "shift_list_screen") {
            ShiftsListScreen(navController = navController)
        }
        composable(route = "add_shift_screen") {
            AddShiftScreen(navController = navController)
        }
    }
}
