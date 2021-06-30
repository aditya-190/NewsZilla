package com.bhardwaj.newszilla.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.view.activities.ActivitySingleNews
import com.bumptech.glide.Glide

class NewsAdapter(var mContext: Context, private var newsList: ArrayList<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var aivNewsImageURL: AppCompatImageView = view.findViewById(R.id.aivNewsImageURL)
        var acTvNewsHeading: AppCompatTextView = view.findViewById(R.id.acTvNewsHeading)
        var acTvNewsDescription: AppCompatTextView = view.findViewById(R.id.acTvNewsDescription)
        var clNewsRootLayout: ConstraintLayout = view.findViewById(R.id.clNewsRootLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentPosition = newsList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.aivNewsImageURL)
        holder.acTvNewsHeading.text = currentPosition.newsHeading
        holder.acTvNewsDescription.text = currentPosition.newsDescription

        holder.clNewsRootLayout.setOnClickListener {
            val intent = Intent(mContext, ActivitySingleNews::class.java)
            intent.putExtra("newsImage", currentPosition.newsImageURL)
            intent.putExtra("newsURL", currentPosition.newsURL)
            intent.putExtra("newsHeading", currentPosition.newsHeading)
            intent.putExtra("newsDescription", currentPosition.newsDescription)
            intent.putExtra("newsContent", currentPosition.newsContent)
            intent.putExtra("newsTime", currentPosition.newsTime)
            intent.putExtra("newsSourceName", currentPosition.newsSourceName)
            intent.putExtra("newsAuthor", currentPosition.newsAuthor)
            intent.putExtra("newsIsBookmarked", currentPosition.newsIsBookmarked)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateNewsList(allNews: List<News>) {
        newsList.clear()
        newsList.addAll(allNews)
        notifyDataSetChanged()
    }
}
