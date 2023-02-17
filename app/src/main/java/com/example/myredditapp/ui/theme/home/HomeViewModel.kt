package com.example.myredditapp.ui.theme.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.myredditapp.network.ApiLoadingResponse
import com.example.myredditapp.network.ApiSuccessResponse
import com.example.myredditapp.network.models.PokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository): ViewModel() {

    var pokemons = mutableStateListOf<PokemonList.PokemonItem>()
        private set

    suspend fun getPokemon() {
        homeRepository.getPokemonAndRefresh().collect { response ->
            when (response) {
                is ApiLoadingResponse -> {
                    Log.d(TAG, "Loading")
                }
                is ApiSuccessResponse -> {
                    pokemons.clear()
                    if(!response.body.results.isNullOrEmpty()) {
                        pokemons.addAll(response.body.results)
                    }
                }
                else -> {
                    Log.d(TAG, "Failure")
                }
            }
        }
    }

}