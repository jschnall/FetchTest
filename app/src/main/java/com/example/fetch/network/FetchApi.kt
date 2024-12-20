package com.example.fetch.network

import kotlinx.coroutines.flow.flow

class FetchApi(private val fetchService: FetchService) {
    fun fetchList() = flow {
        emit(fetchService.list())
    }
}