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
import com.bhardwaj.newszilla.utils.Common
import com.bhardwaj.newszilla.view.activities.ActivitySingleNews
import com.bumptech.glide.Glide

class OnTopicNewsAdapter(
    var mContext: Context,
    private var onTopicNewsList: ArrayList<News>
) :
    RecyclerView.Adapter<OnTopicNewsAdapter.OnTopicNewsViewHolder>() {

    inner class OnTopicNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var aivNewsImageURL: AppCompatImageView = view.findViewById(R.id.aivNewsImageURL)
        var acTvNewsHeading: AppCompatTextView = view.findViewById(R.id.acTvNewsHeading)
        var acTvNewsDescription: AppCompatTextView = view.findViewById(R.id.acTvNewsDescription)
        var clBookmarkRootLayout: ConstraintLayout = view.findViewById(R.id.clBookmarkRootLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnTopicNewsViewHolder {
        return OnTopicNewsViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_bookmark_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnTopicNewsViewHolder, position: Int) {
        val currentPosition = onTopicNewsList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.aivNewsImageURL)
        holder.acTvNewsHeading.text = currentPosition.newsHeading
        holder.acTvNewsDescription.text = currentPosition.newsDescription

        holder.clBookmarkRootLayout.setOnClickListener {
            Common.openNewsActivity(mContext, currentPosition)
        }
    }

    override fun getItemCount(): Int {
        return onTopicNewsList.size
    }
}
