package com.android.searchingimages.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoResponse(
    val meta: Meta,
    val documents: MutableList<VideoDocument>
)

data class VideoDocument(
    val title: String,
    val url: String,
    @SerializedName("datetime")
    val dateTime: Date,
    @SerializedName("play_time")
    val playTime: Int,
    val thumbnail: String,
    val author: String
)