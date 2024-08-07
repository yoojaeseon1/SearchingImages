package com.android.searchingimages.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.searchingimages.databinding.RecyclerViewImageItemBinding
import com.android.searchingimages.databinding.RecyclerViewVideoItemBinding


class ContentListAdapter : ListAdapter<ContentItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<ContentItem>(){
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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ImageHolder(binding: RecyclerViewImageItemBinding) : RecyclerView.ViewHolder(binding.root){
        val thumbnailUrl = binding.ivThumbnail
        val siteName = binding.tvSiteName
        val datetime = binding.tvDatetime
    }

    class VideoHolder(binding: RecyclerViewVideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnailUrl = binding.ivThumbnail
        val siteName = binding.tvSiteName
        val datetime = binding.tvDatetime
    }

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
    }

}