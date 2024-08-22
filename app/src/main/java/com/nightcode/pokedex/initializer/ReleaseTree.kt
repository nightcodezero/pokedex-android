package com.nightcode.pokedex.initializer

import android.util.Log
import timber.log.Timber


class ReleaseTree : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?,
    ) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
    }
}

