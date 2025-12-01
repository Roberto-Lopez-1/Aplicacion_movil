package com.example.level_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level_up.model.News
import com.example.level_up.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news = _news.asStateFlow()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val fetchedNews = RetrofitClient.instance.getNews()
                _news.value = fetchedNews
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
