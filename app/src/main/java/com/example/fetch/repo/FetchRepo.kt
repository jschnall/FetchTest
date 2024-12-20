package com.example.fetch.repo

import com.example.fetch.network.FetchDataItem
import com.example.fetch.network.FetchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FetchRepo(private val fetchApi: FetchApi) {
    fun fetchList(): Flow<List<FetchDataItem>> {
        return flow {
            fetchApi.fetchList()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    if (e is retrofit2.HttpException) {
                        when (e.code()) {
                            404 -> throw Exception("List unavailable")
                            else -> throw e
                        }
                    }
                    throw e
                }
                .collect { items ->
                    emit(items)
                }
        }
    }
}