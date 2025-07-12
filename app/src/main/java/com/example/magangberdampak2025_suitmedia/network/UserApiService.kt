package com.example.magangberdampak2025_suitmedia.network

import com.example.magangberdampak2025_suitmedia.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("x-api-key") xApiKey: String,
    ): Response<ApiResponse>
}
