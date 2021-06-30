package com.bhardwaj.newszilla.repository.api

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.NewsZillaInstance
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface NetworkRequest {
    suspend fun fetchNews(newsZillaInstance: NewsZillaInstance) =
        suspendCoroutine<ArrayList<News>> { cont ->
            val stringRequest: StringRequest = object : StringRequest(
                Method.GET,
                "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a09d149c35f34c0eb39485201d16e546",
                Response.Listener { response ->
                    val totalNews: ArrayList<News> = ArrayList()
                    val totalObject = JSONObject(response)
                    val allNews = totalObject.getJSONArray("articles")
                    for (i in 0 until allNews.length()) {
                        val singleObject = allNews.getJSONObject(i)
                        val newsImageURL = singleObject.getString("urlToImage")
                        val newsURL = singleObject.getString("url")
                        val newsHeading = singleObject.getString("title")
                        val newsDescription = singleObject.getString("description")
                        val newsTime = singleObject.getString("publishedAt")
                        val newsSourceName =
                            singleObject.getJSONObject("source").getString("name")
                        val newsAuthor = singleObject.getString("author")
                        val newsContent = singleObject.getString("content")

                        totalNews.add(
                            News(
                                newsImageURL = newsImageURL,
                                newsURL = newsURL,
                                newsHeading = newsHeading,
                                newsDescription = newsDescription,
                                newsContent = newsContent,
                                newsTime = newsTime,
                                newsSourceName = newsSourceName,
                                newsAuthor = newsAuthor,
                                newsIsBookmarked = false
                            )
                        )
                    }

                    cont.resume(totalNews)
                },
                Response.ErrorListener { error ->
                    Log.d("Aditya", "Error when fetching News: $error")
                }) {}
            newsZillaInstance.addToRequestQueue(stringRequest)
        }
}