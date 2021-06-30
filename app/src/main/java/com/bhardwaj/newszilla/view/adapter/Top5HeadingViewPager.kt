package com.bhardwaj.newszilla.view.adapter

import android.content.Context
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
import com.bumptech.glide.Glide

class Top5HeadingViewPager(
    var mContext: Context,
    private var top5HeadingList: ArrayList<News>
) :
    RecyclerView.Adapter<Top5HeadingViewPager.Top5HeadingViewHolder>() {

    inner class Top5HeadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var aivNewsImageURL: AppCompatImageView = itemView.findViewById(R.id.aivNewsImageURL)
        var acTvNewsDescription: AppCompatTextView = itemView.findViewById(R.id.acTvNewsDescription)
        var clTop5HeadingRootLayout: ConstraintLayout =
            itemView.findViewById(R.id.clTop5HeadingRootLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top5HeadingViewHolder {
        return Top5HeadingViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.single_carousel_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Top5HeadingViewHolder, position: Int) {
        val currentPosition = top5HeadingList[position]

        Glide.with(mContext).load(currentPosition.newsImageURL)
            .placeholder(R.drawable.placeholder_image).into(holder.aivNewsImageURL)
        holder.acTvNewsDescription.text = currentPosition.newsHeading

        holder.clTop5HeadingRootLayout.setOnClickListener {
            Common.openNewsActivity(mContext, currentPosition)
        }
    }

    override fun getItemCount(): Int {
        return top5HeadingList.size
    }

    fun updateHeadingList(updatedHeadingList: List<News>) {
        top5HeadingList.clear()
        top5HeadingList.addAll(updatedHeadingList)
        notifyDataSetChanged()
    }
}