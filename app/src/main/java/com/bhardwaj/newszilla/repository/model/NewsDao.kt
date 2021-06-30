package com.bhardwaj.newszilla.repository.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(news: News)

    @Query("UPDATE news_table SET bookmarked = 0 WHERE heading = :heading")
    suspend fun deleteBookmark(heading: String)

    @Query("SELECT * FROM news_table WHERE bookmarked = 1 ORDER BY time DESC")
    fun getBookmarks(): LiveData<List<News>>

    @Query("SELECT * FROM news_table WHERE type = 'news' ORDER BY time DESC")
    fun getNews(): LiveData<List<News>>

    @Query("SELECT * FROM news_table WHERE type = 'story' ORDER BY time DESC")
    fun getStory(): LiveData<List<News>>

    @Query("SELECT * FROM news_table WHERE type = 'heading' ORDER BY time DESC")
    fun getHeadlines(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsList: ArrayList<News>)
}