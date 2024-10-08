package com.nightcode.pokedex.data.remote.service

import com.nightcode.pokedex.data.remote.model.PokemonInfo
import com.nightcode.pokedex.data.remote.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(
        @Path("name") name: String,
    ): Response<PokemonInfo>
}