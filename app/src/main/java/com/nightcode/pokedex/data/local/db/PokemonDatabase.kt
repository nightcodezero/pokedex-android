package com.nightcode.pokedex.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nightcode.pokedex.data.local.db.dao.PokemonDao
import com.nightcode.pokedex.data.local.db.entity.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var instance: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, PokemonDatabase::class.java, "pokemon_database")
                    .build()
                    .also { instance = it }
            }
        }
    }
}