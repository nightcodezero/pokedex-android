package com.nightcode.pokedex.data.repository

import com.nightcode.pokedex.data.remote.model.PokemonInfo
import com.nightcode.pokedex.data.remote.model.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun fetchPokemonList(
        offset: Int,
        limit: Int,
    ): Flow<Result<PokemonResponse>>

    suspend fun fetchPokemonInfo(name: String): Flow<Result<PokemonInfo>>

    suspend fun saveFavoritePokemon(name: String)

    suspend fun getFavoritePokemon(): List<String>

    suspend fun removeFavoritePokemon(name: String)
}