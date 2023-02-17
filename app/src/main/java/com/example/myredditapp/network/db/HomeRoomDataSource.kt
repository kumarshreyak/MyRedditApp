package com.example.myredditapp.network.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myredditapp.network.models.PokemonList
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeRoomDataSource {
    @Query("SELECT * from pokemonItem")
    fun getAllPokemon(): Flow<List<PokemonList.PokemonItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokemon(pokemon: List<PokemonList.PokemonItem>)
}