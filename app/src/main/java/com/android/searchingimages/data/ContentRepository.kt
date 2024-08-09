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

    //    fun communicateNetwork(context: AppCompatActivity, param: HashMap<String, String>) {
//    fun searchImageFromAPI(context: AppCompatActivity, param: HashMap<String, String>) : MutableList<ImageDocument> {
//        var documents = mutableListOf<ImageDocument>()
//        context.lifecycleScope.launch {
//            val responseData = NetworkClient.contentNetwork.getImages(param)
//            documents = responseData.documents
////            Log.d("content repository", "documents size = ${documents.size}")
////            for (document in documents) {
////                Log.d("content repository", "${document}")
////            }
//        }
//        Log.d("content repository", "documents size = ${documents.size}")
//
//        return documents
//
//    }

//    suspend fun searchImageFromAPI(param: HashMap<String, String>) : MutableList<ImageDocument> {
    suspend fun searchImageFromAPI(param: HashMap<String, String>) : ImageResponse {
        val responseData = NetworkClient.contentNetwork.getImages(param)
//        return responseData.documents
        return responseData

    }

//    fun searchVideoFromAPI(context: AppCompatActivity, param: HashMap<String, String>) {
//
//        context.lifecycleScope.launch {
//            val responseData = NetworkClient.contentNetwork.getVideos(param)
//            val documents = responseData.documents
////            for (document in documents) {
////                Log.d("content repository", "${document}")
////            }
//        }
//    }

//    suspend fun searchVideoFromAPI(param: HashMap<String, String>) : MutableList<VideoDocument> {
    suspend fun searchVideoFromAPI(param: HashMap<String, String>) : VideoResponse {
        val responseData = NetworkClient.contentNetwork.getVideos(param)
//        return responseData.documents
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


//    fun selectAllFavoriteContents(context: Activity): MutableMap<String, ContentItem> {
    fun selectAllFavoriteContents(context: Activity): MutableList<ContentItem> {
//        val contentItems = mutableMapOf<String, ContentItem>()
        val contentItems = ArrayList<ContentItem>()
    Log.d("ContentRepository", "before contentItems hashCode = ${contentItems.hashCode()}")

        val pref = context.getSharedPreferences("favorites", 0)

        val allItems = pref.all
        val gson = Gson()

        for (content in allItems) {
//            contentItems.put(
//                content.key,
//                gson.fromJson(content.value as String, ContentItem::class.java)
//            )
            contentItems.add(
                    gson.fromJson(content.value as String, ContentItem::class.java)
                )

//        for (contentItem in contentItems) {
//            Log.d("contentRepository", "${contentItem}")
//        }

        }

        Log.d("ContentRepository", "after contentItems hashCode = ${contentItems.hashCode()}")
        Log.d("ContentRepository", "after contentItems hashCode = ${contentItems.toMutableList().hashCode()}")

//        return contentItems.toMutableList()
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