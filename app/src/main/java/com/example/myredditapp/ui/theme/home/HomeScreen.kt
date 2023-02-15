package com.example.myredditapp.ui.theme.home

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myredditapp.network.ApiSuccessResponse
import com.example.myredditapp.util.ACCESS_TOKEN
import com.example.myredditapp.util.dataStore
import com.example.myredditapp.util.writeData
import kotlinx.coroutines.flow.collectLatest


const val TAG = "HomeScreen"
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = null) {
        homeViewModel.getAccessToken()
//            .observe(owner) { response ->
//            if(response is ApiSuccessResponse)
//                Log.d(TAG, "Success")
//            else
//                Log.d(TAG, "Failure")
//        }
    }
    Text(
        text = "Hello",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onSurface
    )
}