package com.android.searchingimages.presentation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.searchingimages.data.ContentRepository

class MainViewModel() : ViewModel() {

    val contentRepository = ContentRepository()

    private val _searchResults = MutableLiveData<MutableList<ContentItem>>()
    val searchResults: MutableLiveData<MutableList<ContentItem>> get() = _searchResults

//    private val _favorites = MutableLiveData<MutableMap<String, ContentItem>>()
//    val favorites: MutableLiveData<MutableMap<String, ContentItem>> get() = _favorites

    private val _favorites = MutableLiveData<MutableList<ContentItem>>()
    val favorites: MutableLiveData<MutableList<ContentItem>> get() = _favorites
//    var favorites = mutableListOf<ContentItem>()
    val entireResults = mutableListOf<ContentItem>()

    private var searchNextIndex = 0

    suspend fun setEntireResults(keyword: String){
        entireResults.clear()

        val imageResults =
            contentRepository.searchImageFromAPI(contentRepository.setRequestParam(keyword))
        val videoResults =
            contentRepository.searchVideoFromAPI(contentRepository.setRequestParam(keyword))

        entireResults.addAll(contentRepository.convertImageToContentItem(imageResults))
        entireResults.addAll(contentRepository.convertVideoToContentItem(videoResults))

        entireResults.sortWith{o1, o2 -> o2.datetime.compareTo(o1.datetime)}

        if(searchResults.value == null)
            searchResults.value = mutableListOf()

        searchNextIndex = 0

        searchResults.value?.clear()
        searchResults.value?.addAll(entireResults.subList(searchNextIndex, searchNextIndex+10))
        searchNextIndex+=10
//
//        Log.d("mainViewModel", "searchResult is null = ${searchResults.value == null}")

    }


    fun addSearchResults() {

        if(searchResults.value == null)
            searchResults.value = mutableListOf()

        Log.d("mainViewModel", "searchNextIndex = ${searchNextIndex}")

        if(searchNextIndex+10 > entireResults.size)
            searchResults.value?.addAll(entireResults.subList(searchNextIndex, entireResults.size))
        else {
            searchResults.value?.addAll(
                entireResults.subList(
                    searchNextIndex,
                    searchNextIndex + 10
                )
            )
            searchNextIndex+=10
        }

    }

    fun saveFavorite(context: Activity, contentItem: ContentItem){
        contentRepository.insertFavoriteContent(context, contentItem)
        if(favorites.value == null)
            favorites.value = mutableListOf()
////            favorites.value = mutableMapOf()
//
        favorites.value?.add(contentItem)
//            if(favorites == null)
//            favorites = mutableListOf()
//            favorites.value = mutableMapOf()

//        favorites.add(contentItem)
//        favorites.value?.add(contentItem.thumbnailUrl, contentItem)
    }

    fun deleteFavorite(context: Activity, contentItem: ContentItem) {
        contentRepository.deleteFavoriteContent(context, contentItem)
        setFavorites(context)
    }

    fun setFavorites(context: Activity) {
        val allFavorites = contentRepository.selectAllFavoriteContents(context)
        Log.d("mainViewModel", "allFavorites = ${allFavorites.hashCode()}")
//        Log.d("mainViewModel", "allFavorites size = ${allFavorites.size}")

//        favorites.value = allFavorites.toMutableList()
        favorites.value = allFavorites
//        favorites = contentRepository.selectAllFavoriteContents(context)
    }

    fun insertSearchKeyword(context: Activity, keyword: String) {
        contentRepository.insertSearchKeyword(context, keyword)
    }

    fun getSearchKeyword(context: Activity): String{
        val searchKeyword = contentRepository.getSearchKeyword(context)
        return searchKeyword
    }
}