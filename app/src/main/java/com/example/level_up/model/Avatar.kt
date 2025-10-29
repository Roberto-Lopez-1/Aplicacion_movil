package com.example.level_up.model

import androidx.annotation.DrawableRes
import com.example.level_up.R

data class Avatar(@DrawableRes val imageRes: Int)

val avatars = listOf(
    Avatar(R.drawable.avatar1),
    Avatar(R.drawable.avatar2),
    Avatar(R.drawable.avatar3),
    Avatar(R.drawable.avatar4),
)
