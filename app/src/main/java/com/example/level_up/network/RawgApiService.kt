package com.example.level_up.network

import com.example.level_up.model.GameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApiService {
    @GET("games")
    suspend fun getGames(@Query("key") apiKey: String): GameResponse
}