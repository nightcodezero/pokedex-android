package com.nightcode.pokedex.data.local

interface LocalDataSource {
    suspend fun saveFavoritePokemon(name: String)

    suspend fun getFavoritePokemon(): List<String>

    suspend fun removeFavoritePokemon(name: String)
}