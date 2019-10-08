package com.example.pusoydos

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pusoydos.Model.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var mCardAdapter: CardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val dealNowButton = findViewById<Button>(R.id.deal_button)
        dealNowButton.setOnClickListener {
            getDeckAsync()
        }
    }

    /**
     * Initializes 4 instances of Player.
     * Observes live data and assigns it to cardlist.
     * Clears playerhand and adds cards from cardList to players.
     * Adds all instances of Players to allPlayer then calls preparerecyclerview
     */
    fun getDeckAsync() {
        val player1 = Player("Bob",mutableListOf())
        val player2 = Player("Peter",mutableListOf())
        val player3 = Player("Karen",mutableListOf())
        val player4 = Player("Jeff",mutableListOf())
        val allPlayer: MutableList<Player> = mutableListOf()
        mainViewModel!!.allCards.observe(this, Observer {  cardList ->
            //println("THIS IS THE VALUE OF CARDLIST $cardList")
            player1.playerHand.clear()
            player2.playerHand.clear()
            player3.playerHand.clear()
            player4.playerHand.clear()
            for (i in 0 until 52){
                when(i){
                    in 0 until 13 -> player1.addCardTohand(cardList[i]) //0 to 12
                    in 13 until 26 -> player2.addCardTohand(cardList[i]) //13 to 25
                    in 26 until 39 -> player3.addCardTohand(cardList[i]) //26 to 38
                    in 39 until 52 -> player4.addCardTohand(cardList[i]) //39 to 51
                }
            }
            allPlayer.add(player1)
            allPlayer.add(player2)
            allPlayer.add(player3)
            allPlayer.add(player4)
            prepareRecyclerView(allPlayer, cardList)
        })

    }

    /**
     * Initializes CardAdapter and passes the values to it
     */
    private fun prepareRecyclerView(playerList: MutableList<Player>?, cardList: List<Cards>?) {//List<Cards>
        mCardAdapter = CardAdapter(playerList, cardList)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            card_recyclerview.setLayoutManager(LinearLayoutManager(this))
        } else {
            card_recyclerview.setLayoutManager(GridLayoutManager(this, 3))
        }
        card_recyclerview.setItemAnimator(DefaultItemAnimator())
        card_recyclerview.setAdapter(mCardAdapter)
    }
}