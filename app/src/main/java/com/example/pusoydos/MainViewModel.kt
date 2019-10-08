package com.example.pusoydos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pusoydos.Model.Cards

/**
 * Initializes the DealerRepository and calls getMutableLiveData() and forwards the result to MainActivity
 */
class MainViewModel : ViewModel() {
    val dealerRepository = DealerRepository()
    val allCards: LiveData<List<Cards>> get() = dealerRepository.getMutableLiveData()
}