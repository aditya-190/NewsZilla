package com.bhardwaj.newszilla.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.bhardwaj.newszilla.R

class FullNewsFragment(private var newsURL: String) : Fragment() {

    private lateinit var mContext: Context
    private lateinit var rlShowNews: RelativeLayout

    fun newInstance(): FullNewsFragment {
        return FullNewsFragment(newsURL)
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
        val view = inflater.inflate(R.layout.full_news_fragment, container, false)
        initialise(view)
        return view
    }

    private fun initialise(view: View) {
        rlShowNews = view.findViewById(R.id.rlShowNews)

        rlShowNews.setOnClickListener {
            CustomTabsIntent.Builder().build().launchUrl(mContext, Uri.parse(newsURL))
        }
    }
}