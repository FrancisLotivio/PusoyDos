package com.example.pusoydos

import com.example.pusoydos.Model.Cards

/**
 * Player class for holding cards assigned to its instances
 */
class Player (val name: String, val playerHand: MutableList<Cards>){
    /**
     * Adds a card to player hand
     */
    fun addCardTohand(card: Cards){
        playerHand.add(card)
    }
}