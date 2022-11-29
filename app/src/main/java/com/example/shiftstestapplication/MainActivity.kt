package com.example.shiftstestapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shiftstestapplication.ui.theme.ShiftsTestApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftsTestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShiftHome("Android")
                }
            }
        }
    }
}

@Composable
fun ShiftHome(name: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "shift_list_screen"
    ) {
        composable(route = "shift_list_screen") {

        }
        composable(
            route = "create_shift_screen",
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShiftsTestApplicationTheme {
        ShiftHome("Android")
    }
}