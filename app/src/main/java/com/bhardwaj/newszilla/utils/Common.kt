package com.bhardwaj.newszilla.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bhardwaj.newszilla.R

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

        fun getNews(): ArrayList<News> {
            val newsList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                newsList.add(
                    News(
                        "", "https://www.geeksforgeeks.org/how-to-use-custom-chrome-tabs-in-android/", "News", "1", "1", "08:00 AM", false
                    )
                )
            }
            return newsList
        }

        fun getTop5Headings(): ArrayList<News> {
            val top5NewsList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                top5NewsList.add(
                    News(
                        "", "", "5 Top News", "2", "2", "08:00 AM", false
                    )
                )
            }
            return top5NewsList
        }

        fun getTopStories(): ArrayList<News> {
            val topStoryList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                topStoryList.add(
                    News(
                        "", "", "Stories", "3", "3", "08:00 AM", false
                    )
                )
            }
            return topStoryList
        }

        fun getTopic(): ArrayList<News> {
            val topicList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                topicList.add(
                    News(
                        "", "", "Topics", "4", "4", "08:00 AM", false
                    )
                )
            }
            return topicList
        }

        fun getOnTopicNews(): ArrayList<News> {
            val onTopicList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                onTopicList.add(
                    News(
                        "", "", "onTopic", "5", "5", "08:00 AM", false
                    )
                )
            }
            return onTopicList
        }

        fun getBookmarks(): ArrayList<News> {
            val bookmarkList: ArrayList<News> = ArrayList()
            for (i in 0 until 10) {
                bookmarkList.add(
                    News(
                        "", "", "Bookmarks", "6", "6", "08:00 AM", false
                    )
                )
            }
            return bookmarkList
        }
    }
}