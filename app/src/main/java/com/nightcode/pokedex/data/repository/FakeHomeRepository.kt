package com.nightcode.pokedex.data.repository

import com.nightcode.pokedex.data.network.model.Pokemon
import com.nightcode.pokedex.data.network.model.PokemonInfo
import com.nightcode.pokedex.data.network.model.PokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHomeRepository : HomeRepository {
    override suspend fun fetchPokemonList(
        offset: Int,
        limit: Int,
    ): Flow<Result<PokemonResponse>> =
        flow {
            emit(
                Result.success(
                    PokemonResponse(
                        count = 1302,
                        next = "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
                        previous = null,
                        results =
                            listOf(
                                Pokemon(
                                    0,
                                    "bulbasaur",
                                    "https://pokeapi.co/api/v2/pokemon/1/",
                                ),
                                Pokemon(
                                    1,
                                    "ivysaur",
                                    "https://pokeapi.co/api/v2/pokemon/2/",
                                ),
                            ),
                    ),
                ),
            )
        }

    override suspend fun fetchPokemonInfo(name: String): Flow<Result<PokemonInfo>> =
        flow {
            emit(
                Result.success(
                    PokemonInfo(
                        id = 1,
                        name = "bulbasaur",
                        height = 7,
                        weight = 69,
                        types = emptyList(),
                        stats = emptyList(),
                        experience = 64,
                        exp = 64,
                    ),
                ),
            )
        }

    override suspend fun saveFavoritePokemon(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritePokemon(): List<String> = emptyList()

    override suspend fun removeFavoritePokemon(name: String) {
        TODO("Not yet implemented")
    }
}