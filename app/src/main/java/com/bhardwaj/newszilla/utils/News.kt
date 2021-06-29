package com.bhardwaj.newszilla.utils

data class News(
    var newsImageURL: String,
    var newsURL: String,
    var newsHeading: String,
    var newsDescription: String,
    var newsContent: String,
    var newsTime: String,
    var newsIsBookmarked: Boolean,
)
