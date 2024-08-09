package com.android.searchingimages.presentation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.searchingimages.data.ContentRepository

class MainViewModel() : ViewModel() {

    private val contentRepository = ContentRepository()

    private val _searchResults = MutableLiveData<MutableList<ContentItem>>()
    val searchResults: MutableLiveData<MutableList<ContentItem>> get() = _searchResults

    private val _favorites = MutableLiveData<MutableList<ContentItem>>()
    val favorites: MutableLiveData<MutableList<ContentItem>> get() = _favorites

    val entireResults = mutableListOf<ContentItem>()

    var imagePage = 1
    var videoPage = 1
    private var isImagePageEnd = false
    private var isVideoPageEnd = false

    suspend fun setEntireResults(keyword: String, context: Activity){

        if(keyword.isEmpty())
            return

        val searchKeyword = contentRepository.getSearchKeyword(context)
        if(searchKeyword != keyword) {
            entireResults.clear()
            imagePage = 1
            videoPage = 1
            isImagePageEnd = false
            isVideoPageEnd = false
        }

        val imageResults =
            contentRepository.searchImageFromAPI(contentRepository.setRequestParam(keyword, imagePage))
        val videoResults =
            contentRepository.searchVideoFromAPI(contentRepository.setRequestParam(keyword, videoPage))

        val currentResult = mutableListOf<ContentItem>()
        currentResult.addAll(contentRepository.convertImageToContentItem(imageResults.documents))
        currentResult.addAll(contentRepository.convertVideoToContentItem(videoResults.documents))

        if(!imageResults.meta.isEnd)
            imagePage++
        else
            isImagePageEnd = true

        if(!videoResults.meta.isEnd)
            videoPage++
        else
            isVideoPageEnd = true


        currentResult.sortWith{o1, o2 -> o2.datetime.compareTo(o1.datetime)}
        entireResults.addAll(currentResult)

    }


    fun saveFavorite(context: Activity, contentItem: ContentItem){
        contentRepository.insertFavoriteContent(context, contentItem)
        if(favorites.value == null)
            favorites.value = mutableListOf()

        favorites.value?.add(contentItem)
    }

    fun deleteFavorite(context: Activity, contentItem: ContentItem) {
        contentRepository.deleteFavoriteContent(context, contentItem)

//        searchResults.value?.forEach {
        entireResults.forEach {
            if(it.thumbnailUrl == contentItem.thumbnailUrl)
                it.isFavorite = false
        }

        setFavorites(context)
    }

    fun setFavorites(context: Activity) {
        val allFavorites = contentRepository.selectAllFavoriteContents(context)
        favorites.value = allFavorites
    }

    fun insertSearchKeyword(context: Activity, keyword: String) {
        contentRepository.insertSearchKeyword(context, keyword)
    }

    fun getSearchKeyword(context: Activity): String{
        val searchKeyword = contentRepository.getSearchKeyword(context)
        return searchKeyword
    }
}