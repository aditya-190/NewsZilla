package com.bhardwaj.newszilla.repository

import com.bhardwaj.newszilla.repository.api.NetworkRequest
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.repository.model.NewsDao
import com.bhardwaj.newszilla.utils.NewsZillaInstance

class NewsRepository(private val newsDao: NewsDao) {
    private val networkRequest: NetworkRequest = TODO()
    val getBookmarks = newsDao.getBookmarks()
    suspend fun insertBookmark(news: News) = newsDao.insertBookmark(news)
    suspend fun deleteBookmark(heading: String) = newsDao.deleteBookmark(heading)
    suspend fun getNews(newsZillaInstance: NewsZillaInstance) =
        networkRequest.fetchNews(newsZillaInstance)
}