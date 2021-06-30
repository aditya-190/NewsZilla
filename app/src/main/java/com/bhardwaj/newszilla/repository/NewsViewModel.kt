package com.bhardwaj.newszilla.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bhardwaj.newszilla.repository.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private var repository: NewsRepository) : ViewModel() {

    val getBookmarks = repository.getBookmarks
    val getNews = repository.getNews
    val getStory = repository.getStory
    val getHeadlines = repository.getHeadlines

    fun insertBookmarks(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBookmark(news)
    }

    fun deleteBookmarks(heading: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBookmark(heading)
    }

    fun insertNews(newsList: ArrayList<News>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNews(newsList)
    }

    class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}