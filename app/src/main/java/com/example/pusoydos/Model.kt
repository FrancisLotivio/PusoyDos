package com.example.pusoydos

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.pusoydos.Model.Deck
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.http.GET

/**
 * POJO model for Deck containing list of Cards.
 */
class Model {
    data class Deck(val success: Boolean, val deck_id: String, val shuffled: String, val remaining: Int, val cards: List<Cards>)
    data class Cards(val image: String, val value: String, val suit: String, val code: String)
}

/**
 * Retrofit interface for REST API calls.
 */
interface RestApiService {
    @GET("new/draw/?count=52")
    fun getDeckAsync(): Deferred<Deck>

    companion object {
        /**
         * Implements retrofit builder
         */
        fun createCorService(): RestApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://deckofcardsapi.com/api/deck/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RestApiService::class.java)
        }
    }
}