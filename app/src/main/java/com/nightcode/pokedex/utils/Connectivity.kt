package com.nightcode.pokedex.utils

import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object Connectivity {
    private const val PING_PORT = 53
    private const val PING_TIMEOUT = 1500

    fun checkInternetConnection(socketFactory: SocketFactory): Result<Unit> =
        runCatching {
            Timber.d("Pinging Google")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", PING_PORT), PING_TIMEOUT)
            socket.close()
        }
}