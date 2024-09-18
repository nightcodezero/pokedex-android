package com.nightcode.pokedex.data.pref

interface LocalDataSource {
    suspend fun saveFavoritePokemon(name: String)

    suspend fun getFavoritePokemon(): List<String>

    suspend fun removeFavoritePokemon(name: String)
}