package com.bhardwaj.newszilla.repository

import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.repository.model.NewsDao

class NewsRepository(private val newsDao: NewsDao) {
    val getBookmarks = newsDao.getBookmarks()

    suspend fun insertBookmark(news: News) = newsDao.insertBookmark(news)
    suspend fun deleteBookmark(heading: String) = newsDao.deleteBookmark(heading)
}