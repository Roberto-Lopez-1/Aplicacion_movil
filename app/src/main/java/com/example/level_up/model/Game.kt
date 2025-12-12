package com.example.level_up.model

import com.google.gson.annotations.SerializedName

data class Game(
    val id: Int,
    val name: String,
    @SerializedName("background_image") val backgroundImage: String,
    val rating: Float
)