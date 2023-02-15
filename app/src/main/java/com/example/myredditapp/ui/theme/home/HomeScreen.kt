package com.example.myredditapp.ui.theme.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = null) {
        homeViewModel.getAccessToken()
    }
    Text(
        text = "Hello",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onSurface
    )
}