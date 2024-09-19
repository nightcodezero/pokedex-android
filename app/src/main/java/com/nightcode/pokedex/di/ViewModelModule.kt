package com.nightcode.pokedex.di

import com.nightcode.pokedex.presentation.ui.info.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModel { PokemonViewModel(get()) }
    }