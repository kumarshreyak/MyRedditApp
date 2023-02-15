package com.example.myredditapp.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myredditapp.network.models.GetAccessTokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel() {

    suspend fun getAccessToken() = homeRepository.getAccessToken().asLiveData()
}