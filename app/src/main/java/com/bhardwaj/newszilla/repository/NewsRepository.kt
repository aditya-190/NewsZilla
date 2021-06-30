package com.bhardwaj.newszilla.repository

import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.repository.model.NewsDao

class NewsRepository(private val newsDao: NewsDao) {
    val getBookmarks = newsDao.getBookmarks()
    val getNews = newsDao.getNews()
    val getStory = newsDao.getStory()
    val getHeadlines = newsDao.getHeadlines()

    suspend fun insertBookmark(news: News) = newsDao.insertBookmark(news)
    suspend fun deleteBookmark(heading: String) = newsDao.deleteBookmark(heading)
    suspend fun insertNews(newsList: ArrayList<News>) = newsDao.insertNews(newsList)
}