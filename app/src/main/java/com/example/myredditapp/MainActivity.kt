package com.example.myredditapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myredditapp.ui.theme.MyRedditAppTheme
import com.example.myredditapp.ui.theme.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()
        setContent {
            MyRedditAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }

    private fun initApp() {

    }

    @Composable
    private fun SetupNavGraph(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController = navController) }
        }
    }
}