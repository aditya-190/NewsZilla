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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.view.activities.ActivityBookmark
import com.bhardwaj.newszilla.view.activities.ActivityMain
import com.bhardwaj.newszilla.view.adapter.TopicsAdapter
import com.bhardwaj.newszilla.viewmodel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverFragment(private var newsViewModel: NewsViewModel) : Fragment() {

    private lateinit var mContext: Context
    private lateinit var aciBookmark: AppCompatImageView
    private lateinit var tvPageBack: TextView

    private lateinit var rvSuggestedTopics: RecyclerView
    private lateinit var topicsList: ArrayList<News>
    private lateinit var topicsAdapter: TopicsAdapter

    fun newInstance(): DiscoverFragment {
        return DiscoverFragment(newsViewModel)
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
        val view = inflater.inflate(R.layout.discover_fragment, container, false)
        initialise(view)
        clickListeners()
        setUpAdapters()
        getTopicsFromAPI()
        return view
    }

    private fun initialise(view: View) {
        topicsList = ArrayList()
        aciBookmark = view.findViewById(R.id.aciBookmark)
        tvPageBack = view.findViewById(R.id.tvPageBack)
        rvSuggestedTopics = view.findViewById(R.id.rvSuggestedTopics)
    }

    private fun clickListeners() {
        tvPageBack.setOnClickListener {
            ActivityMain.vpActivityMain.currentItem = 1
        }

        aciBookmark.setOnClickListener {
            startActivity(Intent(mContext, ActivityBookmark::class.java))
        }
    }

    private fun setUpAdapters() {
        rvSuggestedTopics.layoutManager = GridLayoutManager(mContext, 3)
        topicsAdapter = TopicsAdapter(mContext, topicsList)
        rvSuggestedTopics.adapter = topicsAdapter
    }

    private fun getTopicsFromAPI() {
        Common.checkConnection(mContext)
        GlobalScope.launch(Dispatchers.IO) {
            topicsList.clear()
            topicsList.addAll(Common.getNews())
            withContext(Dispatchers.Main) {
                topicsAdapter.notifyDataSetChanged()
            }
        }
    }
}