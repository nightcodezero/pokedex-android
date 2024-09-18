package com.nightcode.pokedex.di

import com.nightcode.pokedex.data.network.RemoteDataSource
import com.nightcode.pokedex.data.network.RemoteDataSourceImpl
import com.nightcode.pokedex.data.pref.Datastore
import com.nightcode.pokedex.data.pref.LocalDataSource
import org.koin.dsl.module

val dataSourceModule =
    module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
        single<LocalDataSource> { Datastore(get()) }
    }