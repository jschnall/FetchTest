package com.example.fetch.network.di

import com.example.fetch.network.FetchApi
import com.example.fetch.network.FetchService
import com.example.fetch.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

fun networkModule() = module {
    single<FetchApi> { FetchApi(get()) }

    single<FetchService> {
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
            .create(FetchService::class.java)
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        builder.build()
    }
}
