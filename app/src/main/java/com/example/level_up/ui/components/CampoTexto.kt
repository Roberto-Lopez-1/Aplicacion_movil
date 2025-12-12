package com.example.level_up.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isError: Boolean = false
) {
    val indicatorColor by animateColorAsState(
        targetValue = if (isError) Color.Red else Color(0xFF7289DA),
        animationSpec = tween(durationMillis = 300)
    )
    val unfocusedIndicatorColor by animateColorAsState(
        targetValue = if (isError) Color.Red else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )
    val cursorColor by animateColorAsState(
        targetValue = if (isError) Color.Red else Color(0xFF7289DA),
        animationSpec = tween(durationMillis = 300)
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = indicatorColor,
            unfocusedIndicatorColor = unfocusedIndicatorColor,
            cursorColor = cursorColor,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text)
    )
}
