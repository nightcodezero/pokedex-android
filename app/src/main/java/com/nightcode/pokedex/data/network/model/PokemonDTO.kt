package com.nightcode.pokedex.data.network.model

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PokemonDTO(
    val id: Int?,
    val name: String?,
    var imageUrl: String?,
    var bgColor: String?,
)

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(
        bundle: Bundle,
        key: String,
    ) = bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(
        bundle: Bundle,
        key: String,
        value: T,
    ) {
        bundle.putString(key, json.encodeToString(value))
    }
}