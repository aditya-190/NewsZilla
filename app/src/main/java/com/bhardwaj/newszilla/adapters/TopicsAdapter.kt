package com.bhardwaj.newszilla.adapters

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
import com.bhardwaj.newszilla.activities.ActivityOnTopicNews
import com.bhardwaj.newszilla.utils.News
import com.bumptech.glide.Glide

class TopicsAdapter(var mContext: Context, private var topicsList: ArrayList<News>) :
    RecyclerView.Adapter<TopicsAdapter.TopicsViewHolder>() {

    inner class TopicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivTopicImage: AppCompatImageView = view.findViewById(R.id.ivTopicImage)
        var acTvTopicsName: AppCompatTextView = view.findViewById(R.id.acTvTopicsName)
        var clTopicsRootLayout: ConstraintLayout = view.findViewById(R.id.clTopicsRootLayout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicsViewHolder {
        return TopicsViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_topics_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        val currentPosition = topicsList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.ivTopicImage)
        holder.acTvTopicsName.text = currentPosition.newsHeading

        holder.clTopicsRootLayout.setOnClickListener {
            val intent = Intent(mContext, ActivityOnTopicNews::class.java)
            intent.putExtra("topicName", currentPosition.newsHeading)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }
}
