package com.nightcode.pokedex.di

import com.nightcode.pokedex.data.network.RemoteDataSource
import com.nightcode.pokedex.data.network.RemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataSourceModule =
    module {
        single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    }