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

    @Query("DELETE FROM news_table WHERE heading = :heading")
    suspend fun deleteBookmark(heading: String)

    @Query("SELECT * FROM news_table WHERE bookmarked = 1 ORDER BY time ASC")
    fun getBookmarks(): LiveData<List<News>>
}