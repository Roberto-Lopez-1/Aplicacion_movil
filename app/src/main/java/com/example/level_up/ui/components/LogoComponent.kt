package com.example.level_up.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.level_up.R

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo_level_up),
        contentDescription = "Logo Level-Up Gamer"
    )
}
