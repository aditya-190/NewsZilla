package com.bhardwaj.newszilla.view.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.utils.NewsZillaInstance
import com.bhardwaj.newszilla.view.adapter.SingleNewsFragmentAdapter
import com.bhardwaj.newszilla.view.fragments.*
import com.bhardwaj.newszilla.repository.NewsViewModel

class ActivitySingleNews : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var singleNewsPageAdapter: SingleNewsFragmentAdapter
    private lateinit var newsImage: String
    private lateinit var newsURL: String
    private lateinit var newsHeading: String
    private lateinit var newsDescription: String
    private lateinit var newsContent: String
    private lateinit var newsTime: String
    private lateinit var newsSourceName: String
    private lateinit var newsAuthor: String
    private lateinit var newsType: String
    private var newsIsBookmarked: Boolean = false
    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.NewsViewModelFactory((application as NewsZillaInstance).repository)
    }

    companion object {
        lateinit var vpActivitySingleNews: ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news)
        initialise()
        Common.checkConnection(mContext)
    }

    private fun initialise() {
        mContext = this@ActivitySingleNews
        newsImage = intent.getStringExtra("newsImage").toString()
        newsURL = intent.getStringExtra("newsURL").toString()
        newsHeading = intent.getStringExtra("newsHeading").toString()
        newsDescription = intent.getStringExtra("newsDescription").toString()
        newsContent = intent.getStringExtra("newsContent").toString()
        newsTime = intent.getStringExtra("newsTime").toString()
        newsSourceName = intent.getStringExtra("newsSourceName").toString()
        newsAuthor = intent.getStringExtra("newsAuthor").toString()
        newsIsBookmarked = intent.getBooleanExtra("newsIsBookmarked", false)
        newsType = intent.getStringExtra("newsType").toString()

        vpActivitySingleNews = findViewById(R.id.vpActivitySingleNews)
        singleNewsPageAdapter = SingleNewsFragmentAdapter(supportFragmentManager, lifecycle)
        singleNewsPageAdapter.addFragment(
            NewsFragment(
                newsImage,
                newsURL,
                newsHeading,
                newsDescription,
                newsContent,
                newsTime,
                newsSourceName,
                newsAuthor,
                newsIsBookmarked,
                newsType,
                newsViewModel,
            ).newInstance()
        )
        singleNewsPageAdapter.addFragment(FullNewsFragment(newsURL).newInstance())
        vpActivitySingleNews.adapter = singleNewsPageAdapter
    }

    override fun onBackPressed() {
        when (vpActivitySingleNews.currentItem) {
            1 -> {
                vpActivitySingleNews.currentItem = 0
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}