package com.android.searchingimages.presentation

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object ContentBindingAdapter {

    @BindingAdapter("items")
    @JvmStatic
    fun RecyclerView.setItems(items: MutableList<ContentItem>?) {
        Log.d("setItems", "items is null = ${items == null}")

//      if(this.adapter == null) {
//          val contentAdapter = ContentListAdapter{
//
//          }
//          this.adapter = contentAdapter
//      }
        val contentAdapter = this.adapter as ContentListAdapter

        contentAdapter.submitList(items?.toMutableList())
    }

    @BindingAdapter("image")
    @JvmStatic
    fun ImageView.setImage(imageUrl: Any?) {
        Glide.with(this.context)
            .load(imageUrl)
            .override(200,200)
            .centerCrop()
            .into(this)
    }

//    @BindingAdapter("favoriteItems")
//    @JvmStatic
//    fun RecyclerView.setItems(items: MutableLiveData<MutableList<ContentItem>>?) {
//        Log.d("favoriteItems", "items is null = ${items?.value == null}")
//
//        if(this.adapter == null) {
//            val contentAdapter = ContentListAdapter{
//
//            }
//            this.adapter = contentAdapter
//        }
//        val contentAdapter = this.adapter as ContentListAdapter
//
//        contentAdapter.submitList(items?.value)
//    }

}