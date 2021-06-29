package com.bhardwaj.newszilla.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bhardwaj.newszilla.R
import com.bhardwaj.newszilla.activities.ActivitySingleNews
import com.bhardwaj.newszilla.utils.News
import com.bumptech.glide.Glide

class AllStoriesAdapter(var mContext: Context, private var topStoryList: ArrayList<News>) :
    RecyclerView.Adapter<AllStoriesAdapter.AllStoriesViewHolder>() {

    inner class AllStoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivTopStoryImage: AppCompatImageView = view.findViewById(R.id.ivTopStoryImage)
        var acTvStoryName: TextView = view.findViewById(R.id.acTvStoryName)
        var clStoryRootLayout: ConstraintLayout = view.findViewById(R.id.clStoryRootLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllStoriesViewHolder {
        return AllStoriesViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_all_story_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllStoriesViewHolder, position: Int) {
        val currentPosition = topStoryList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.ivTopStoryImage)
        holder.acTvStoryName.text = currentPosition.newsHeading

        holder.clStoryRootLayout.setOnClickListener {
            val intent = Intent(mContext, ActivitySingleNews::class.java)
            intent.putExtra("newsImage", currentPosition.newsImageURL)
            intent.putExtra("newsURL", currentPosition.newsURL)
            intent.putExtra("newsHeading", currentPosition.newsHeading)
            intent.putExtra("newsDescription", currentPosition.newsDescription)
            intent.putExtra("newsContent", currentPosition.newsContent)
            intent.putExtra("newsTime", currentPosition.newsTime)
            intent.putExtra("newsIsBookmarked", currentPosition.newsIsBookmarked)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return topStoryList.size
    }
}
