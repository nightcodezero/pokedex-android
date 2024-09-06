package com.nightcode.pokedex.data.pref

interface LocalDataSource {
    fun saveFavoritePokemon(name: String)

    fun removeFavoritePokemon(name: String)
}