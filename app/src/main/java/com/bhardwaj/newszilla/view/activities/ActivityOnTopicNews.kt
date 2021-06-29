package com.bhardwaj.newszilla.view.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.view.adapter.OnTopicNewsAdapter
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.repository.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityOnTopicNews : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var tvPageBack: TextView
    private lateinit var tvTopicName: TextView
    private lateinit var nsvOnTopicNewsRoot: NestedScrollView

    private lateinit var onTopicNewsLists: ArrayList<News>
    private lateinit var onTopicNewsAdapter: OnTopicNewsAdapter
    private lateinit var rvOnTopicNews: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_topic_news)
        initialise()
        clickListeners()
        setUpAdapters()
        getOnTopicNewsFromAPI()
    }

    private fun initialise() {
        mContext = this@ActivityOnTopicNews
        onTopicNewsLists = ArrayList()
        tvTopicName = findViewById(R.id.tvTopicName)
        tvPageBack = findViewById(R.id.tvPageBack)
        nsvOnTopicNewsRoot = findViewById(R.id.nsvOnTopicNewsRoot)
        rvOnTopicNews = findViewById(R.id.rvOnTopicNews)

        tvTopicName.text = intent.getStringExtra("topicName")
    }

    private fun clickListeners() {
        tvPageBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpAdapters() {
        rvOnTopicNews.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        onTopicNewsAdapter = OnTopicNewsAdapter(mContext, onTopicNewsLists)
        rvOnTopicNews.adapter = onTopicNewsAdapter
    }

    private fun getOnTopicNewsFromAPI() {
        Common.checkConnection(mContext)

        GlobalScope.launch(Dispatchers.IO) {
            onTopicNewsLists.clear()
            onTopicNewsLists.addAll(Common.getOnTopicNews())
            withContext(Dispatchers.Main) {
                onTopicNewsAdapter.notifyDataSetChanged()
            }
        }
    }
}