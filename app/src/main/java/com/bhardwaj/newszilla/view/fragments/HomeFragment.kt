package com.bhardwaj.newszilla.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.view.activities.ActivityAllStories
import com.bhardwaj.newszilla.view.activities.ActivityMain.Companion.vpActivityMain
import com.bhardwaj.newszilla.view.adapter.NewsAdapter
import com.bhardwaj.newszilla.view.adapter.Top5HeadingViewPager
import com.bhardwaj.newszilla.view.adapter.TopStoryAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mainSwipeRefresh: SwipeRefreshLayout

    private lateinit var tvPageBack: TextView
    private lateinit var aciPageRefresh: AppCompatImageView

    private lateinit var top5HeadingLists: ArrayList<News>
    private lateinit var vpTop5News: ViewPager2
    private lateinit var tvSeeAllStories: TextView

    private lateinit var topStoriesLists: ArrayList<News>
    private lateinit var topStoryAdapter: TopStoryAdapter
    private lateinit var rvTopStories: RecyclerView

    private lateinit var newsLists: ArrayList<News>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var rvNews: RecyclerView

    fun newInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        initialise(view)
        clickListeners()
        setUpAdapters()
        getNewsFromAPI()
        return view
    }

    private fun initialise(view: View) {
        top5HeadingLists = ArrayList()
        topStoriesLists = ArrayList()
        newsLists = ArrayList()
        mainSwipeRefresh = view.findViewById(R.id.mainSwipeRefresh)
        tvPageBack = view.findViewById(R.id.tvPageBack)
        aciPageRefresh = view.findViewById(R.id.aciPageRefresh)
        vpTop5News = view.findViewById(R.id.vpTop5News)
        tvSeeAllStories = view.findViewById(R.id.tvSeeAllStories)
        rvTopStories = view.findViewById(R.id.rvTopStories)
        rvNews = view.findViewById(R.id.rvNews)
    }

    private fun setUpAdapters() {
        vpTop5News.adapter = Top5HeadingViewPager(mContext, top5HeadingLists)

        rvTopStories.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        topStoryAdapter = TopStoryAdapter(mContext, topStoriesLists)
        rvTopStories.adapter = topStoryAdapter

        rvNews.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter(mContext, newsLists)
        rvNews.adapter = newsAdapter
    }

    private fun clickListeners() {
        tvPageBack.setOnClickListener {
            vpActivityMain.currentItem = 0
        }

        tvSeeAllStories.setOnClickListener {
            val intent = Intent(mContext, ActivityAllStories::class.java)
            intent.putExtra("topStoriesLists", Gson().toJson(topStoriesLists))
            startActivity(intent)
        }

        aciPageRefresh.setOnClickListener {
            getNewsFromAPI()
        }

        mainSwipeRefresh.setOnRefreshListener {
            getNewsFromAPI()
        }
    }

    private fun getNewsFromAPI() {
        mainSwipeRefresh.isRefreshing = true
        Common.checkConnection(mContext)

        GlobalScope.launch(Dispatchers.IO) {
            top5HeadingLists.clear()
            top5HeadingLists.addAll(Common.getTop5Headings())
            withContext(Dispatchers.Main) {
                vpTop5News.adapter?.notifyDataSetChanged()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            topStoriesLists.clear()
            topStoriesLists.addAll(Common.getTopStories())
            withContext(Dispatchers.Main) {
                topStoryAdapter.notifyDataSetChanged()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            newsLists.clear()
            newsLists.addAll(Common.getNews())
            withContext(Dispatchers.Main) {
                newsAdapter.notifyDataSetChanged()
                mainSwipeRefresh.isRefreshing = false
            }
        }
    }
}