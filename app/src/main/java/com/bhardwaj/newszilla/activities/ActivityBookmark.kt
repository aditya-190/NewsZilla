package com.bhardwaj.newszilla.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.adapters.BookmarkAdapter
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.utils.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityBookmark : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var tvPageBack: TextView
    private lateinit var nsvBookmarkRoot: NestedScrollView

    private lateinit var bookmarkLists: ArrayList<News>
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var rvBookmarks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)
        initialise()
        clickListeners()
        setUpAdapters()
        getBookMarkFromDB()
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
    }

    private fun getBookMarkFromDB() {
        Common.checkConnection(mContext)
        GlobalScope.launch(Dispatchers.IO) {
            bookmarkLists.clear()
            bookmarkLists.addAll(Common.getBookmarks())
            withContext(Dispatchers.Main) {
                bookmarkAdapter.notifyDataSetChanged()
            }
        }
    }
}