package com.example.myredditapp.ui.theme.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myredditapp.network.models.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = null) {
        withContext(Dispatchers.IO) {
            homeViewModel.getPokemon()
        }
    }

    LazyColumn {
        itemsIndexed(homeViewModel.pokemons) { i, pokemon ->
            PokemonRow(pokemon)
        }
    }
}

@Composable
fun PokemonRow(pokemon: PokemonList.PokemonItem) {
    Text(
        text = pokemon.name.orEmpty(),
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}