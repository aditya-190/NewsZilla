package com.bhardwaj.newszilla.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.Common.Companion.convertTimeToLocale
import com.bhardwaj.newszilla.view.activities.ActivitySingleNews.Companion.vpActivitySingleNews
import com.bhardwaj.newszilla.viewmodel.NewsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class NewsFragment(
    private var newsImage: String,
    private var newsURL: String,
    private var newsHeading: String,
    private var newsDescription: String,
    private var newsContent: String,
    private var newsTime: String,
    private var newsSourceName: String,
    private var newsAuthor: String,
    private var newsIsBookmarked: Boolean,
    private var newsViewModel: NewsViewModel
) : Fragment() {

    private lateinit var mContext: Context
    private lateinit var ivSingleNews1: ImageView
    private lateinit var ivSingleNews2: ImageView
    private lateinit var tvNewsHeading: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvReadMore: TextView
    private lateinit var tvContent: TextView
    private lateinit var fabShare: FloatingActionButton
    private lateinit var fabBookmark: FloatingActionButton
    private lateinit var clNewsFragmentRoot: ConstraintLayout

    fun newInstance(): NewsFragment {
        return NewsFragment(
            newsImage,
            newsURL,
            newsHeading,
            newsDescription,
            newsContent,
            newsTime,
            newsSourceName,
            newsAuthor,
            newsIsBookmarked,
            newsViewModel
        )
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
        val view = inflater.inflate(R.layout.news_fragment, container, false)
        initialise(view)
        clickListeners()
        return view
    }

    private fun initialise(view: View) {
        ivSingleNews1 = view.findViewById(R.id.ivSingleNews1)
        ivSingleNews2 = view.findViewById(R.id.ivSingleNews2)
        tvNewsHeading = view.findViewById(R.id.tvNewsHeading)
        tvDescription = view.findViewById(R.id.tvDescription)
        tvContent = view.findViewById(R.id.tvContent)
        tvDate = view.findViewById(R.id.tvDate)
        tvReadMore = view.findViewById(R.id.tvReadMore)
        fabShare = view.findViewById(R.id.fabShare)
        fabBookmark = view.findViewById(R.id.fabBookmark)
        clNewsFragmentRoot = view.findViewById(R.id.clNewsFragmentRoot)

        Glide.with(mContext).load(newsImage).placeholder(R.drawable.placeholder_image)
            .into(ivSingleNews1)
        Glide.with(mContext).load(newsImage).thumbnail().placeholder(R.drawable.placeholder_image)
            .into(ivSingleNews2)
        tvNewsHeading.text = newsHeading
        tvDescription.text = newsDescription
        tvContent.text = newsContent
        tvDate.text = convertTimeToLocale(mContext, newsTime)
    }

    private fun clickListeners() {
        fabBookmark.setOnClickListener {
            Snackbar.make(clNewsFragmentRoot, "Bookmark Removed.", Snackbar.LENGTH_SHORT).show()
            newsIsBookmarked = if (newsIsBookmarked) {
                newsViewModel.deleteBookmarks(heading = newsHeading)
                false
            } else {
                Snackbar.make(clNewsFragmentRoot, "Bookmark Added.", Snackbar.LENGTH_SHORT).show()
                newsViewModel.insertBookmarks(
                    News(
                        newsImageURL = newsImage,
                        newsURL = newsURL,
                        newsHeading = newsHeading,
                        newsDescription = newsDescription,
                        newsContent = newsContent,
                        newsTime = newsTime,
                        newsSourceName = newsSourceName,
                        newsAuthor = newsAuthor,
                        newsIsBookmarked = !newsIsBookmarked
                    )
                )
                true
            }
        }

        fabShare.setOnClickListener {
            Toast.makeText(mContext, "Share Clicked", Toast.LENGTH_SHORT).show()
        }

        ivSingleNews2.setOnClickListener {
            vpActivitySingleNews.currentItem = 1
        }
    }
}