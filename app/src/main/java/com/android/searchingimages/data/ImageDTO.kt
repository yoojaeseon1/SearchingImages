package com.android.searchingimages.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ImageResponse(
    val meta: Meta,
    val documents: MutableList<ImageDocument>
)


data class ImageDocument(
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val width: Int,
    val height: Int,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("doc_url")
    val docUrl: String,
    @SerializedName("datetime")
    val dateTime: Date
    )