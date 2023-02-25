package com.example.myredditapp.ui.theme.home

import com.example.myredditapp.network.models.PokemonList

fun getPokemonList() = PokemonList(
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