package com.example.level_up.games

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.model.Game
import com.example.level_up.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GamesViewModel : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchGames() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getGames(apiKey = "09f252ad84b549e9b08b78a23d460eaa")
                _games.value = response.results
            } catch (e: Exception) {
                Log.e("GamesViewModel", "Error fetching games", e)
                _error.value = e.message
            }
        }
    }
}