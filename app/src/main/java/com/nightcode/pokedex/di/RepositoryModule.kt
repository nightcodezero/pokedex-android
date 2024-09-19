package com.nightcode.pokedex.di

import com.nightcode.pokedex.data.repository.HomeRepository
import com.nightcode.pokedex.data.repository.HomeRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule =
    module {
        single<HomeRepository> { HomeRepositoryImpl(get(), get(qualifier = named("room"))) }
    }