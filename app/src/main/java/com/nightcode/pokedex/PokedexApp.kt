package com.nightcode.pokedex

import android.app.Application
import com.nightcode.pokedex.di.networkModule
import com.nightcode.pokedex.di.remoteDataSourceModule
import com.nightcode.pokedex.di.repositoryModule
import com.nightcode.pokedex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            modules(viewModelModule, repositoryModule, networkModule, remoteDataSourceModule)
        }
    }
}