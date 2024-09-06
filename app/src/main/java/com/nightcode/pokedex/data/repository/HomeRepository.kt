package com.nightcode.pokedex.data.repository

import com.nightcode.pokedex.data.network.model.PokemonInfo
import com.nightcode.pokedex.data.network.model.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun fetchPokemonList(): Flow<Result<PokemonResponse>>

    suspend fun fetchPokemonInfo(name: String): Flow<Result<PokemonInfo>>
}