package com.nightcode.pokedex.data.repository

import com.nightcode.pokedex.data.local.LocalDataSource
import com.nightcode.pokedex.data.network.RemoteDataSource
import com.nightcode.pokedex.data.network.model.PokemonInfo
import com.nightcode.pokedex.data.network.model.PokemonResponse
import com.nightcode.pokedex.data.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : HomeRepository {
    override suspend fun fetchPokemonList(
        offset: Int,
        limit: Int,
    ): Flow<Result<PokemonResponse>> =
        flow {
            emit(
                safeApiCall {
                    remote.fetchPokemonList(offset, limit)
                },
            )
        }

    override suspend fun fetchPokemonInfo(name: String): Flow<Result<PokemonInfo>> =
        flow {
            emit(
                safeApiCall {
                    remote.fetchPokemonInfo(name)
                },
            )
        }

    override suspend fun saveFavoritePokemon(name: String) {
        local.saveFavoritePokemon(name)
    }

    override suspend fun getFavoritePokemon(): List<String> = local.getFavoritePokemon()

    override suspend fun removeFavoritePokemon(name: String) {
        local.removeFavoritePokemon(name)
    }
}