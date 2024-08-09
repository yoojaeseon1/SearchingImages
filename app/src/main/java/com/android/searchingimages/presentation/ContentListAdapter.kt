package com.android.searchingimages.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.searchingimages.SearchingUtils
import com.android.searchingimages.databinding.RecyclerViewImageItemBinding
import com.android.searchingimages.databinding.RecyclerViewVideoItemBinding
import com.bumptech.glide.Glide


class ContentListAdapter(val onClick: (item: ContentItem, holder: RecyclerView.ViewHolder) -> Unit) : ListAdapter<ContentItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<ContentItem>(){
    override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem.thumbnailUrl == newItem.thumbnailUrl
    }

    override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).contentType) {
            ContentType.IMAGE -> TYPE_IMAGE
            ContentType.VIDEO -> TYPE_VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("contentListAdapter", "onCreateViewHolder()")

        return when(viewType) {
            TYPE_IMAGE -> {
                val binding = RecyclerViewImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ImageHolder(binding)
            }
            // TYPE_VIDEO
            else -> {
                val binding = RecyclerViewVideoItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                VideoHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try{
            val item = getItem(position)
            when(item.contentType) {
                ContentType.IMAGE -> {
                    val castedHolder = holder as ImageHolder
                    Glide.with(castedHolder.thumbnailUrl.context)
                        .load(item.thumbnailUrl)
                        .centerCrop()
                        .into(castedHolder.thumbnailUrl)

                    val siteName = ContentType.IMAGE.type + item.siteName
                    castedHolder.siteName.text = siteName
                    castedHolder.datetime.text = SearchingUtils.convertDateFormat(item.datetime)
                    if(item.isFavorite)
                        castedHolder.favorite.isVisible = true
                    castedHolder.root.setOnClickListener{
                        onClick(item, holder)
                    }
                }
                ContentType.VIDEO -> {
                    val castedHolder = holder as VideoHolder

                    Glide.with(castedHolder.thumbnailUrl.context)
                        .load(item.thumbnailUrl)
                        .centerCrop()
                        .into(castedHolder.thumbnailUrl)
                    val siteName = ContentType.VIDEO.type + item.siteName
                    castedHolder.siteName.text = siteName
                    castedHolder.datetime.text = SearchingUtils.convertDateFormat(item.datetime)
                    if(item.isFavorite)
                        castedHolder.favorite.isVisible = true
                    castedHolder.root.setOnClickListener{
                        onClick(item, holder)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ContentListAdapter", "exception : ${e.message}")
        }
    }

    class ImageHolder(val binding: RecyclerViewImageItemBinding) : RecyclerView.ViewHolder(binding.root){
        val thumbnailUrl = binding.ivThumbnail
        val siteName = binding.tvSiteName
        val datetime = binding.tvDatetime
        val favorite = binding.ivFavorite
        val root = binding.root
    }

    class VideoHolder(val binding: RecyclerViewVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnailUrl = binding.ivThumbnail
        val siteName = binding.tvSiteName
        val datetime = binding.tvDatetime
        val favorite = binding.ivFavorite
        val root = binding.root
    }

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
    }

}