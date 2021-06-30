package com.bhardwaj.newszilla.view.adapter

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
import com.bhardwaj.newszilla.repository.model.News
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.view.activities.ActivitySingleNews
import com.bumptech.glide.Glide

class TopStoryAdapter(var mContext: Context, private var topStoryList: ArrayList<News>) :
    RecyclerView.Adapter<TopStoryAdapter.TopStoryViewHolder>() {

    inner class TopStoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivTopStoryImage: AppCompatImageView = view.findViewById(R.id.ivTopStoryImage)
        var acTvStoryName: TextView = view.findViewById(R.id.acTvStoryName)
        var clStoryRootLayout: ConstraintLayout = view.findViewById(R.id.clStoryRootLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopStoryViewHolder {
        return TopStoryViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_story_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopStoryViewHolder, position: Int) {
        val currentPosition = topStoryList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.ivTopStoryImage)
        holder.acTvStoryName.text = currentPosition.newsSourceName

        holder.clStoryRootLayout.setOnClickListener {
            Common.openNewsActivity(mContext, currentPosition)
        }
    }

    override fun getItemCount(): Int {
        return topStoryList.size
    }

    fun updateStoryList(updatedStoryList: List<News>) {
        topStoryList.clear()
        topStoryList.addAll(updatedStoryList)
        notifyDataSetChanged()
    }
}
