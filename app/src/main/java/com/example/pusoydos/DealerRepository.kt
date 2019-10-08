package com.example.pusoydos

import androidx.lifecycle.MutableLiveData
import com.example.pusoydos.Model.*
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * Repository that starts a network request with retrofit.
 */
class DealerRepository {
    private var listOfCards = mutableListOf<Cards>()
    private var mutableLiveData = MutableLiveData<List<Cards>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val thisApiCorService by lazy {
        RestApiService.createCorService()
    }

    /**
     * Launches a coroutine for the API request which is observable in MainActivity.
     * returns a mutablelivedata containing all the Cards in the Deck
     */
    fun getMutableLiveData():MutableLiveData<List<Cards>> {
        coroutineScope.launch {
            val request = thisApiCorService.getDeckAsync()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    val mDeck = response
                    listOfCards = mDeck.cards as MutableList<Cards>
                    mutableLiveData.value=listOfCards
                } catch (e: HttpException) {
                    // Log exception //
                    println("ERROR EXCEPTION $e")

                } catch (e: Throwable) {
                    // Log error //
                    println("ERROR THROWABLE $e")
                }
            }
        }
        return mutableLiveData
    }
}