package com.example.fetch.ui.di

import com.example.fetch.repo.di.repoModule
import com.example.fetch.ui.FetchListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {
    includes(repoModule)

    viewModel { FetchListViewModel(get()) }
}