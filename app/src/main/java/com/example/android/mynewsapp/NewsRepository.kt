package com.example.android.mynewsapp

import android.util.Log

const val country_us = "us"
const val status = "ok"

class NewsRepository {

    private val TAG = NewsRepository::class.java.canonicalName

    suspend fun getDailyFeed(): List<DailyArticle> {
        val newsFeed =
            DailyNewsService.retroNewsService.getDailyNewsAsync(country = country_us)

        return try {
            val feed = newsFeed.await()
            if (feed.status == status) feed.articles else emptyList()
        }  catch (e: Exception) {
            Log.d(TAG, "Stacktrace", e)
            emptyList()
        }

    }
}

// Error thrown when exception is present while performing the request
class RequestError(message: String, cause: Throwable?) : Throwable(message, cause)