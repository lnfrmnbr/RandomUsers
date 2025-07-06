package com.example.randomusers.data.network

import com.example.randomusers.data.remote.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/?results=20")
    suspend fun getUsers(): Response<UserResponse>
}
