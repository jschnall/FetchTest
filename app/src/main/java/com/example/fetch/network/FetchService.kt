package com.example.fetch.network

import retrofit2.http.GET

interface FetchService {
    @GET("hiring.json")
    suspend fun list(): List<FetchDataItem>
}