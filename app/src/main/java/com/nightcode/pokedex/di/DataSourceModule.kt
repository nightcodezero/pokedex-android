package com.nightcode.pokedex.di

import com.nightcode.pokedex.data.local.LocalDataSource
import com.nightcode.pokedex.data.local.db.PokemonDatabase
import com.nightcode.pokedex.data.local.db.RoomLocalDataSource
import com.nightcode.pokedex.data.local.db.dao.PokemonDao
import com.nightcode.pokedex.data.local.pref.DSLocalDataSource
import com.nightcode.pokedex.data.network.RemoteDataSource
import com.nightcode.pokedex.data.network.RemoteDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule =
    module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
        single<PokemonDatabase> { PokemonDatabase.getDatabase(get()) }
        single<PokemonDao> { get<PokemonDatabase>().pokemonDao() }
        single<LocalDataSource>(qualifier = named("datastore")) { DSLocalDataSource(get()) }
        single<LocalDataSource>(qualifier = named("room")) { RoomLocalDataSource(get()) }
    }