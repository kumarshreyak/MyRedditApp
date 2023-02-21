package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.db.HomeRoomDataSource
import com.example.myredditapp.network.models.PokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeHomeRoomDataSource : HomeRoomDataSource {
    var pokeData: MutableStateFlow<List<PokemonList.PokemonItem>> = MutableStateFlow(listOf())

    override fun getAllPokemon(): Flow<List<PokemonList.PokemonItem>> {
        return pokeData
    }

    override fun insertAllPokemon(pokemon: List<PokemonList.PokemonItem>) {
        pokeData.value = pokemon
    }

}