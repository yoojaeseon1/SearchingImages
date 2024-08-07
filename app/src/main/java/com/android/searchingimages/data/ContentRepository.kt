package com.android.searchingimages.data

import android.media.Image
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.searchingimages.presentation.ContentItem
import com.android.searchingimages.presentation.ContentType
import kotlinx.coroutines.launch

class ContentRepository {


    fun setRequestParam(keyword: String): HashMap<String, String> {
        return hashMapOf(
            "query" to keyword,
            "sort" to "recency",
            "size" to "10"
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

    suspend fun searchImageFromAPI(param: HashMap<String, String>) : MutableList<ImageDocument> {
        val responseData = NetworkClient.contentNetwork.getImages(param)
        return responseData.documents

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

    suspend fun searchVideoFromAPI(param: HashMap<String, String>) : MutableList<VideoDocument> {
        val responseData = NetworkClient.contentNetwork.getVideos(param)
        return responseData.documents
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
                    ContentType.VIDEO
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


}