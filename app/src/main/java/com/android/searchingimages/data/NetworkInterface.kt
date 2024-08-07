package com.android.searchingimages.data

import com.android.searchingimages.SearchingInfo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface NetworkInterface {

    @Headers(SearchingInfo.HEADER)
    @GET("image")
    suspend fun getImages(@QueryMap param: HashMap<String, String>): ImageResponse


    @Headers(SearchingInfo.HEADER)
    @GET("vclip")
    suspend fun getVideos(@QueryMap param: HashMap<String, String>): VideoResponse



}