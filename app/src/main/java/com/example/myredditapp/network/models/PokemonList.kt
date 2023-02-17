package com.example.myredditapp.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PokemonList(
    val count: Int?,
    val next: String?,
    val previous: Any?,
    val results: List<PokemonItem>?
) {
    @Entity
    data class PokemonItem(
        @PrimaryKey val name: String,
        val url: String?
    )
}