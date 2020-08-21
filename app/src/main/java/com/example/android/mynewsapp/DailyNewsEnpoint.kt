package com.example.android.mynewsapp

import android.os.Parcelable
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "1437be957ba74f9e93cf1688a28a05ac"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(API_BASE_URL)
    .build()

// DailyNews Service API
interface DailyNewsApi {

    @GET("top-headlines")
    fun getDailyNewsAsync(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<DailyNewsResponse>
}

object DailyNewsService {
    val retroNewsService: DailyNewsApi by lazy {
        retrofit.create(DailyNewsApi::class.java)
    }
}

@Parcelize
data class DailyNewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<DailyArticle>
) : Parcelable

@Parcelize
data class DailyArticle(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable

@Parcelize
data class NewsSource(
    val id: String,
    val name: String
) : Parcelable
