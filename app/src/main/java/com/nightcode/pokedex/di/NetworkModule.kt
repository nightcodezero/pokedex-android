package com.nightcode.pokedex.di

import com.nightcode.pokedex.BuildConfig
import com.nightcode.pokedex.data.remote.service.PokedexService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient =
    OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        ).readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType(),
            ),
        ).build()

fun provideService(retrofit: Retrofit): PokedexService = retrofit.create(PokedexService::class.java)

val networkModule =
    module {
        single<OkHttpClient> { provideHttpClient() }
        single<Retrofit> { provideRetrofit(get()) }
        single<PokedexService> { provideService(get()) }
    }