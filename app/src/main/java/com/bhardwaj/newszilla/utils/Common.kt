package com.bhardwaj.newszilla.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.NewsViewModel
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.view.activities.ActivitySingleNews
import kotlinx.coroutines.Job
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.suspendCoroutine

class Common {
    companion object {
        fun checkConnection(mContext: Context) {
            val dialog = Dialog(mContext)
            dialog.setContentView(R.layout.no_internet_dialog)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            val checkInternet: RelativeLayout = dialog.findViewById(R.id.rlTryAgain)
            checkInternet.setOnClickListener {
                showOrRemoveDialog(mContext, dialog)
            }
            showOrRemoveDialog(mContext, dialog)
        }

        private fun showOrRemoveDialog(mContext: Context, dialog: Dialog) {
            if (isOnline(mContext)) {
                dialog.dismiss()
            } else {
                dialog.show()
            }
        }

        private fun isOnline(mContext: Context): Boolean {
            val connectivityManager =
                mContext.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected && netInfo.isAvailable
        }

        fun convertTimeToLocale(mContext: Context, time: String): String {
            return SimpleDateFormat(
                mContext.getString(R.string.date_format),
                Locale.getDefault()
            ).format(
                SimpleDateFormat(
                    mContext.getString(R.string.default_time_format),
                    Locale.getDefault()
                ).parse(time)!!
            )
        }

        fun openNewsActivity(mContext: Context, currentPosition: News) {
            val intent = Intent(mContext, ActivitySingleNews::class.java)
            intent.putExtra("newsImage", currentPosition.newsImageURL)
            intent.putExtra("newsURL", currentPosition.newsURL)
            intent.putExtra("newsHeading", currentPosition.newsHeading)
            intent.putExtra("newsDescription", currentPosition.newsDescription)
            intent.putExtra("newsContent", currentPosition.newsContent)
            intent.putExtra("newsTime", currentPosition.newsTime)
            intent.putExtra("newsSourceName", currentPosition.newsSourceName)
            intent.putExtra("newsAuthor", currentPosition.newsAuthor)
            intent.putExtra("newsIsBookmarked", currentPosition.newsIsBookmarked)
            intent.putExtra("newsType", currentPosition.newsType)
            mContext.startActivity(intent)
        }

        suspend fun fetchNews(
            url: String,
            newsType: String,
            newsZillaInstance: NewsZillaInstance?,
            newsViewModel: NewsViewModel
        ) =
            suspendCoroutine<Job> { cont ->
                val stringRequest: StringRequest = object : StringRequest(
                    Method.GET, url,
                    Response.Listener { response ->
                        val totalObject = JSONObject(response)
                        val totalNews: ArrayList<News> = ArrayList()
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
                            val singleNews = News(
                                newsImageURL = newsImageURL,
                                newsURL = newsURL,
                                newsHeading = newsHeading,
                                newsDescription = newsDescription,
                                newsContent = newsContent,
                                newsTime = newsTime,
                                newsSourceName = newsSourceName,
                                newsAuthor = newsAuthor,
                                newsIsBookmarked = false,
                                newsType = newsType
                            )
                            totalNews.add(singleNews)
                        }
                        newsViewModel.insertNews(totalNews)
                    },
                    Response.ErrorListener { error ->
                        Log.d("Aditya", "Error when fetching News: $error")
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val params: HashMap<String, String> = HashMap()
                        params["User-Agent"] = "Mozilla/5.0"
                        return params
                    }
                }
                newsZillaInstance?.addToRequestQueue(stringRequest)
            }
    }
}