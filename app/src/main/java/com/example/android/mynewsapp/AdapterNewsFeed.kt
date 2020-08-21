package com.example.android.mynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.android.mynewsapp.databinding.NewsItemBinding

class AdapterNewsFeed(private val onClickListener: OnClickListener) :
    ListAdapter<DailyArticle, AdapterNewsFeed.FeedItemViewHolder>(CallBack) {

    class FeedItemViewHolder(private var binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: DailyArticle) {
            binding.itemModel = newsItem
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object CallBack : DiffUtil.ItemCallback<DailyArticle>() {
        override fun areItemsTheSame(oldItem: DailyArticle, newItem: DailyArticle): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DailyArticle, newItem: DailyArticle): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        return FeedItemViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {

        val feedItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(feedItem)
        }
        val image =
            holder.itemView.findViewById<ImageView>(R.id.image)
        Glide.with(image.context)
            .load(feedItem.urlToImage).transition(DrawableTransitionOptions.withCrossFade())
            .into(image);

        holder.bind(feedItem)    }


    class OnClickListener(val clickListener: (dailyArticle: DailyArticle) -> Unit) {
        fun onClick(dailyArticle: DailyArticle) = clickListener(dailyArticle)
    }

}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DailyArticle>?) {
    val adapter = recyclerView.adapter as AdapterNewsFeed
    adapter.submitList(data)
}