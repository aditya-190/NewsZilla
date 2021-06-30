package com.bhardwaj.newszilla.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.NewsZillaInstance
import com.bhardwaj.newszilla.view.adapter.BookmarkAdapter
import com.bhardwaj.newszilla.repository.NewsViewModel

class ActivityBookmark : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var tvPageBack: TextView
    private lateinit var nsvBookmarkRoot: NestedScrollView

    private lateinit var bookmarkLists: ArrayList<News>
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var rvBookmarks: RecyclerView
    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.NewsViewModelFactory((application as NewsZillaInstance).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        initialise()
        clickListeners()
        setUpAdapters()
    }

    private fun initialise() {
        mContext = this@ActivityBookmark
        bookmarkLists = ArrayList()
        tvPageBack = findViewById(R.id.tvPageBack)
        nsvBookmarkRoot = findViewById(R.id.nsvBookmarkRoot)
        rvBookmarks = findViewById(R.id.rvBookmarks)
    }

    private fun clickListeners() {
        tvPageBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpAdapters() {
        rvBookmarks.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        bookmarkAdapter = BookmarkAdapter(mContext, bookmarkLists)
        rvBookmarks.adapter = bookmarkAdapter

        newsViewModel.getBookmarks.observe(this) { bookmarkedNews ->
            bookmarkedNews?.let { bookmarkAdapter.updateBookmarksList(it) }
        }
    }
}