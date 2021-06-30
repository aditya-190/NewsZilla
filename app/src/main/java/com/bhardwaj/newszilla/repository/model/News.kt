package com.bhardwaj.newszilla.repository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class News(
    @ColumnInfo(name = "image") val newsImageURL: String,
    @ColumnInfo(name = "news") val newsURL: String,
    @PrimaryKey @ColumnInfo(name = "heading") val newsHeading: String,
    @ColumnInfo(name = "description") val newsDescription: String,
    @ColumnInfo(name = "content") val newsContent: String,
    @ColumnInfo(name = "time") val newsTime: String,
    @ColumnInfo(name = "source") val newsSourceName: String,
    @ColumnInfo(name = "author") val newsAuthor: String,
    @ColumnInfo(name = "bookmarked", defaultValue = "0") val newsIsBookmarked: Boolean,
    @ColumnInfo(name = "type") val newsType: String,
)