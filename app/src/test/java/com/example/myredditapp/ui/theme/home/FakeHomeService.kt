package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.HomeService
import com.example.myredditapp.network.models.PokemonList
import retrofit2.Response
import retrofit2.http.Query

class FakeHomeService: HomeService {
    override suspend fun getPokemon(
        @Query(value = "limit") limit: Int,
        @Query(value = "offset") offset: Int
    ): Response<PokemonList> {
        return if (limit == 0) {
            Response.error(500, null)
        } else {
            Response.success(
                PokemonList(
                    count = 1,
                    next = null,
                    previous = null,
                    results = listOf(
                        PokemonList.PokemonItem(name = "pikachu", "pikachuUrl"),
                        PokemonList.PokemonItem(name = "bulbasaur", "bulbasaurUrl"),
                        PokemonList.PokemonItem(name = "charmander", "charmanderUrl"),
                        PokemonList.PokemonItem(name = "squirtle", "squirtleUrl"),
                    )
                )
            )
        }
    }
}