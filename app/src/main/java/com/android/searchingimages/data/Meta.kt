package com.android.searchingimages.data

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val PageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)
