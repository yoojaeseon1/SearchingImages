package com.android.searchingimages

import java.text.SimpleDateFormat
import java.util.Date

object SearchingUtils {


    fun convertDateFormat(date: Date): String{
        val dateFormat = "yyyy-MM-dd HH:mm:ss"
        return SimpleDateFormat(dateFormat).format(date)
    }




}