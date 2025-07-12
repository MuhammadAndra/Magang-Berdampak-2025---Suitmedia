package com.example.magangberdampak2025_suitmedia.model

import com.example.magangberdampak2025_suitmedia.network.RetrofitClient

class UserRepository {
    suspend fun fetchUsers(page: Int, perPage: Int): List<User> {
        return try {
            val response =
                RetrofitClient.userApi.getUsers(page = page, perPage = perPage, xApiKey = "reqres-free-v1")
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}