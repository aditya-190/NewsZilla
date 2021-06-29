package com.bhardwaj.newszilla.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.view.adapter.AllStoriesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActivityAllStories : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var tvPageBack: TextView
    private lateinit var nsvAllStoriesRoot: NestedScrollView

    private lateinit var storiesLists: ArrayList<News>
    private lateinit var storiesAdapter: AllStoriesAdapter
    private lateinit var rvAllStories: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_stories)
        initialise()
        clickListeners()
        setUpAdapters()
    }

    private fun initialise() {
        mContext = this@ActivityAllStories
        storiesLists = ArrayList()
        tvPageBack = findViewById(R.id.tvPageBack)
        nsvAllStoriesRoot = findViewById(R.id.nsvAllStoriesRoot)
        rvAllStories = findViewById(R.id.rvAllStories)
    }

    private fun clickListeners() {
        tvPageBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpAdapters() {
        storiesLists = Gson().fromJson(
            intent.getStringExtra("topStoriesLists").toString(),
            object : TypeToken<ArrayList<News>>() {}.type
        )

        rvAllStories.layoutManager = GridLayoutManager(mContext, 3)
        storiesAdapter = AllStoriesAdapter(mContext, storiesLists)
        rvAllStories.adapter = storiesAdapter
    }
}