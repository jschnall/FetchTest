package com.example.fetch.repo.di

import com.example.fetch.network.di.networkModule
import com.example.fetch.repo.FetchRepo
import org.koin.dsl.module

val repoModule = module {
    includes(networkModule())

    single<FetchRepo> { FetchRepo(get()) }
}