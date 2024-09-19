package com.nightcode.pokedex.data.remote

import com.nightcode.pokedex.data.remote.model.PokemonInfo
import com.nightcode.pokedex.data.remote.model.PokemonResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun fetchPokemonList(
        offset: Int,
        limit: Int,
    ): Response<PokemonResponse>

    suspend fun fetchPokemonInfo(name: String): Response<PokemonInfo>
}