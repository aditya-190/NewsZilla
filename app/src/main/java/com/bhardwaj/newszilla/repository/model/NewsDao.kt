package com.bhardwaj.newszilla.repository.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(news: News)

    @Query("UPDATE news_table SET bookmarked = 0 WHERE heading = :heading")
    suspend fun deleteBookmark(heading: String)

    @Query("SELECT * FROM news_table WHERE bookmarked = 1 ORDER BY time DESC")
    fun getBookmarks(): LiveData<List<News>>

    @Query("SELECT * FROM news_table ORDER BY time DESC")
    fun getNews(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: News)
}