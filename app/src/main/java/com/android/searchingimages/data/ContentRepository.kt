package com.android.searchingimages.data

import android.app.Activity
import android.util.Log
import com.android.searchingimages.SearchingInfo
import com.android.searchingimages.presentation.ContentItem
import com.android.searchingimages.presentation.ContentType
import com.google.gson.Gson

class ContentRepository {


    fun setRequestParam(keyword: String, page: Int): HashMap<String, String> {
        return hashMapOf(
            "query" to keyword,
            "sort" to "recency",
            "size" to "5",
            "page" to page.toString()
        )
    }

    suspend fun searchImageFromAPI(param: HashMap<String, String>) : ImageResponse {
        val responseData = NetworkClient.contentNetwork.getImages(param)
        return responseData

    }

    suspend fun searchVideoFromAPI(param: HashMap<String, String>) : VideoResponse {
        val responseData = NetworkClient.contentNetwork.getVideos(param)
        return responseData
    }

    fun convertImageToContentItem(
        contents: MutableList<ImageDocument>,
    ): MutableList<ContentItem> {
        val contentItems = mutableListOf<ContentItem>()
        contents.forEach {
            contentItems.add(
                ContentItem(
                    it.thumbnailUrl,
                    it.displaySitename,
                    it.dateTime,
                    ContentType.IMAGE
                )
            )
        }

        return contentItems
    }

    fun convertVideoToContentItem(contents: MutableList<VideoDocument>): MutableList<ContentItem> {
        val contentItems = mutableListOf<ContentItem>()
        contents.forEach {
            contentItems.add(ContentItem(it.thumbnail, it.title, it.dateTime, ContentType.VIDEO))
        }
        return contentItems
    }

    fun insertFavoriteContent(context: Activity, contentItem: ContentItem) {

        val pref = context.getSharedPreferences("favorites", 0)
        val edit = pref.edit()
        val gson = Gson()

        val toJson = gson.toJson(contentItem)

        edit.putString(contentItem.thumbnailUrl, toJson)
        edit.apply()
    }

    fun deleteFavoriteContent(context: Activity, contentItem: ContentItem) {
        val pref = context.getSharedPreferences("favorites", 0)
        val edit = pref.edit()
        edit.remove(contentItem.thumbnailUrl)
        edit.apply()
    }


    fun selectAllFavoriteContents(context: Activity): MutableList<ContentItem> {
        val contentItems = ArrayList<ContentItem>()

        val pref = context.getSharedPreferences("favorites", 0)

        val allItems = pref.all
        val gson = Gson()

        for (content in allItems) {
            contentItems.add(
                    gson.fromJson(content.value as String, ContentItem::class.java)
                )
        }

        return mutableListOf<ContentItem>().apply { addAll(contentItems) }
    }

    fun insertSearchKeyword(context: Activity, keyword: String){
        val pref = context.getSharedPreferences("keyword", 0)
        val edit = pref.edit()
        edit.putString(SearchingInfo.INIT_KEYWORD, keyword)
        edit.apply()
    }

    fun getSearchKeyword(context: Activity): String {
        val pref = context.getSharedPreferences("keyword", 0)
        val keyword = pref.getString(SearchingInfo.INIT_KEYWORD, "")?: ""
        return keyword
    }

}