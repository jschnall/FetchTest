package com.example.fetch.network

import kotlinx.serialization.Serializable

@Serializable
data class FetchDataItem(val id: Int, val listId: Int, val name: String?)