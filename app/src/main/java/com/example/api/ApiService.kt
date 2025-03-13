package com.example.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

const val BASE_URL = "http://localhost:5000"

data class RegisterRequest(val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)
data class LocationRequest(val token: String, val latitude: Double, val longitude: Double)

interface ApiService {
    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): retrofit2.Response<Unit>

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<LoginResponse>

    @POST("/location")
    suspend fun sendLocation(@Body request: LocationRequest): retrofit2.Response<Unit>
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
