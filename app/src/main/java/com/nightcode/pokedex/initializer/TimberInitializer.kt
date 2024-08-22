package com.nightcode.pokedex.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import com.nightcode.pokedex.BuildConfig

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.d("TimberInitializer is initialized.")
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf()
}

