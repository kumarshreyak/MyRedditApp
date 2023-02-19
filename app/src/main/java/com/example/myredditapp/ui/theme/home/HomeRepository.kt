package com.example.myredditapp.ui.theme.home

import android.util.Log
import com.example.myredditapp.network.*
import com.example.myredditapp.network.db.HomeRoomDataSource
import com.example.myredditapp.network.models.ListingRequest
import com.example.myredditapp.network.models.PokemonList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val homeRoomDataSource: HomeRoomDataSource
) {
    suspend fun getPokemonAndRefresh(
        request: ListingRequest = ListingRequest(limit = 25)
    ): Flow<ApiResponse<PokemonList>> {
        return flow {
            emit(ApiLoadingResponse())
            val dbflow = homeRoomDataSource.getAllPokemon()
            dbflow.first { pokemons ->
                Log.d("HomeRepo", "Data from db - ${pokemons.size}")
                emit(
                    ApiSuccessResponse(
                        body = PokemonList(
                            results = pokemons,
                            count = pokemons.size,
                            next = null,
                            previous = null
                        )
                    )
                )
                true
            }
            val response = handleApi {
                homeService.getPokemon(
                    limit = request.limit,
                    offset = request.offset
                )
            }
            if (response is ApiSuccessResponse) {
                if(!response.body.results.isNullOrEmpty()) {
                    Log.d("HomeRepo", "Data updated - ${response.body.results.size}")
                    homeRoomDataSource.insertAllPokemon(response.body.results)
                }
            }
            dbflow.collect { pokemons ->
                Log.d("HomeRepo", "Data from db - ${pokemons.size}")
                emit(
                    ApiSuccessResponse(
                        body = PokemonList(
                            results = pokemons,
                            count = pokemons.size,
                            next = null,
                            previous = null
                        )
                    )
                )
            }
        }
    }
}