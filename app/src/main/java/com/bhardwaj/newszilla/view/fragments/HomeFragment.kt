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
import com.bhardwaj.newszilla.repository.NewsViewModel
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.utils.NewsZillaInstance
import com.bhardwaj.newszilla.view.activities.ActivityAllStories
import com.bhardwaj.newszilla.view.activities.ActivityMain.Companion.vpActivityMain
import com.bhardwaj.newszilla.view.adapter.NewsAdapter
import com.bhardwaj.newszilla.view.adapter.Top5HeadingViewPager
import com.bhardwaj.newszilla.view.adapter.TopStoryAdapter
import com.google.gson.Gson

class HomeFragment(private var newsViewModel: NewsViewModel) : Fragment() {

    private lateinit var mContext: Context
    private var newsZillaInstance: NewsZillaInstance? = null
    private lateinit var mainSwipeRefresh: SwipeRefreshLayout

    private lateinit var tvPageBack: TextView
    private lateinit var aciPageRefresh: AppCompatImageView

    private lateinit var top5HeadingLists: ArrayList<News>
    private lateinit var vpTop5News: ViewPager2
    private lateinit var vpTop5NewsAdapter: Top5HeadingViewPager
    private lateinit var tvSeeAllStories: TextView

    private lateinit var topStoriesLists: ArrayList<News>
    private lateinit var topStoryAdapter: TopStoryAdapter
    private lateinit var rvTopStories: RecyclerView

    private lateinit var newsLists: ArrayList<News>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var rvNews: RecyclerView

    fun newInstance(): HomeFragment {
        return HomeFragment(newsViewModel)
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
        getNewsFromDB()
        return view
    }

    private fun initialise(view: View) {
        newsZillaInstance = NewsZillaInstance.instances
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
        vpTop5NewsAdapter = Top5HeadingViewPager(mContext, top5HeadingLists)
        vpTop5News.adapter = vpTop5NewsAdapter

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
        Common.checkConnection(mContext)
       // newsViewModel.clearDataBefore()

        newsViewModel.getNewsFromAPI(
            "https://newsapi.org/v2/top-headlines?country=in&pageSize=20&apiKey=a09d149c35f34c0eb39485201d16e546",
            "heading",
            newsZillaInstance,
            newsViewModel
        )

        newsViewModel.getNewsFromAPI(
            "https://newsapi.org/v2/everything?language=en&q=computer&sortBy=popularity&pageSize=100&apiKey=a09d149c35f34c0eb39485201d16e546",
            "news",
            newsZillaInstance,
            newsViewModel
        )

        newsViewModel.getNewsFromAPI(
            "https://newsapi.org/v2/top-headlines?country=in&pageSize=20&page=2&apiKey=a09d149c35f34c0eb39485201d16e546",
            "story",
            newsZillaInstance,
            newsViewModel
        )
    }

    private fun getNewsFromDB() {
        newsViewModel.getNews.observe(viewLifecycleOwner) { news ->
            news?.let { newsAdapter.updateNewsList(news) }
            mainSwipeRefresh.isRefreshing = false
        }

        newsViewModel.getStory.observe(viewLifecycleOwner) { story ->
            story?.let { topStoryAdapter.updateStoryList(story) }
        }

        newsViewModel.getHeadlines.observe(viewLifecycleOwner) { heading ->
            heading?.let { vpTop5NewsAdapter.updateHeadingList(heading) }
        }
    }
}