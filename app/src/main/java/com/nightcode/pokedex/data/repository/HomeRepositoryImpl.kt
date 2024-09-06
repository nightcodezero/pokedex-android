package com.nightcode.pokedex.data.repository

import com.nightcode.pokedex.data.network.RemoteDataSource
import com.nightcode.pokedex.data.network.model.PokemonInfo
import com.nightcode.pokedex.data.network.model.PokemonResponse
import com.nightcode.pokedex.data.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val remote: RemoteDataSource,
) : HomeRepository {
    override suspend fun fetchPokemonList(): Flow<Result<PokemonResponse>> =
        flow {
            emit(
                safeApiCall {
                    remote.fetchPokemonList(0, 20)
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
}