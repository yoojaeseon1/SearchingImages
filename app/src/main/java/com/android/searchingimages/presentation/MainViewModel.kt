package com.android.searchingimages.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.searchingimages.data.ContentRepository
import com.android.searchingimages.data.ImageDocument

class MainViewModel(val contentRepository: ContentRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<MutableList<ContentItem>>()
    val searchResults: MutableLiveData<MutableList<ContentItem>> get() = _searchResults

    private val _favorites = MutableLiveData<MutableList<ContentItem>>()
    val favorites: MutableLiveData<MutableList<ContentItem>> get() = _favorites

    val entireResults = mutableListOf<ContentItem>()

    suspend fun setEntireResults(keyword: String){
        val imageResults =
            contentRepository.searchImageFromAPI(contentRepository.setRequestParam(keyword))
        val videoResults =
            contentRepository.searchVideoFromAPI(contentRepository.setRequestParam(keyword))

        entireResults.addAll(contentRepository.convertImageToContentItem(imageResults))
        entireResults.addAll(contentRepository.convertVideoToContentItem(videoResults))

        searchResults.value = mutableListOf()

        searchResults.value?.addAll(entireResults.subList(0,9))
//
//        Log.d("mainViewModel", "searchResult is null = ${searchResults.value == null}")

    }


    suspend fun setSearchResults() {
        searchResults.value?.addAll(entireResults.subList(0, 9))
    }

}