package com.nightcode.pokedex.data.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon",
    indices = [Index(value = ["name"], unique = true)],
)
data class Pokemon(
    @PrimaryKey var id: Int? = null,
    val name: String,
)