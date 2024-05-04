package com.example.android.mynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil.*
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.android.myapplication.R
import com.example.android.myapplication.databinding.NewsItemBinding

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

    companion object CallBack : ItemCallback<DailyArticle>() {
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

        // get a reference to the image view where we'll be loading an image onto
        val image =
            holder.itemView.findViewById<ImageView>(R.id.image)

        // pass the image view's context to the Glide's library with method to specify it's context
        Glide.get(image.context).setMemoryCategory(MemoryCategory.HIGH)
        Glide.with(image.context)
                // pass the image remote url
            .load(feedItem.urlToImage)
                // add the type of transition to display when loading
            .transition(DrawableTransitionOptions.withCrossFade())
            // and finally the reference to the image view we are going to be loading it to.
            .into(image)

        holder.bind(feedItem)
    }


    class OnClickListener(val clickListener: (dailyArticle: DailyArticle) -> Unit) {
        fun onClick(dailyArticle: DailyArticle) = clickListener(dailyArticle)
    }

}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DailyArticle>?) {
    val adapter = recyclerView.adapter as AdapterNewsFeed
    adapter.submitList(data)
}