package com.nightcode.pokedex.data.network

import com.nightcode.pokedex.data.network.service.PokedexService

class RemoteDataSourceImpl(
    private val pokedexService: PokedexService,
) : RemoteDataSource {
    override suspend fun fetchPokemonList(
        offset: Int,
        limit: Int,
    ) = pokedexService.fetchPokemonList(offset, limit)

    override suspend fun fetchPokemonInfo(name: String) = pokedexService.fetchPokemonInfo(name)
}