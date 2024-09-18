package com.nightcode.pokedex.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Pokedex")
val FAVORITE_POKEMON = stringPreferencesKey("favorite_pokemon")

class Datastore(
    private val context: Context,
) : LocalDataSource {
    override suspend fun saveFavoritePokemon(name: String) {
        context.dataStore.edit { preferences ->
            val favoritePokemon = preferences[FAVORITE_POKEMON] ?: ""
            preferences[FAVORITE_POKEMON] = "$favoritePokemon,$name"
        }
    }

    override suspend fun getFavoritePokemon(): List<String> =
        context.dataStore.data
            .map { preferences ->
                preferences[FAVORITE_POKEMON]?.split(",")?.filter { it.isNotEmpty() }?.distinct()
                    ?: emptyList()
            }.first()

    override suspend fun removeFavoritePokemon(name: String) {
        context.dataStore.edit { preferences ->
            val favoritePokemon = preferences[FAVORITE_POKEMON] ?: ""
            preferences[FAVORITE_POKEMON] = favoritePokemon.replace(name, "")
        }
    }
}