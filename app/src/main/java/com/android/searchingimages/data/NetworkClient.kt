package com.android.searchingimages.data

import com.android.searchingimages.BuildConfig
import com.android.searchingimages.SearchingInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {


    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val contentRetrofit = Retrofit.Builder()
        .baseUrl(SearchingInfo.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            createOkHttpClient()
        ).build()

    val contentNetwork: NetworkInterface = contentRetrofit.create(NetworkInterface::class.java)

}