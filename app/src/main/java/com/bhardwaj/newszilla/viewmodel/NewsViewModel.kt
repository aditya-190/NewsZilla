package com.bhardwaj.newszilla.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bhardwaj.newszilla.repository.NewsRepository
import com.bhardwaj.newszilla.repository.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private var repository: NewsRepository) : ViewModel() {

    val getBookmarks = repository.getBookmarks

    fun insertBookmarks(news: News) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBookmark(news)
    }

    fun deleteBookmarks(heading: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBookmark(heading)
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