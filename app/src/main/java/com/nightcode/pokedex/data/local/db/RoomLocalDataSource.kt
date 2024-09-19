package com.nightcode.pokedex.data.local.db

import com.nightcode.pokedex.data.local.LocalDataSource
import com.nightcode.pokedex.data.local.db.dao.PokemonDao
import com.nightcode.pokedex.data.local.db.entity.Pokemon

class RoomLocalDataSource(
    private val pokemonDao: PokemonDao,
) : LocalDataSource {
    override suspend fun saveFavoritePokemon(name: String) {
        pokemonDao.insert(Pokemon(name = name))
    }

    override suspend fun getFavoritePokemon(): List<String> = pokemonDao.getAllPokemon().map { it.name }

    override suspend fun removeFavoritePokemon(name: String) {
        val pokemon = pokemonDao.getPokemonByName(name)
        pokemonDao.delete(pokemon)
    }
}