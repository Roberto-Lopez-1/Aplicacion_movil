package com.example.level_up.network

import com.example.level_up.model.News
import retrofit2.http.GET

interface NewsApi {
    @GET("news")
    suspend fun getNews(): List<News>
}
