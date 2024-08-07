package com.android.searchingimages.presentation

import java.util.Date


data class ContentItem(val thumbnailUrl: String,
                       val siteName: String,
                       val datetime: Date,
                       val contentType: ContentType)