package com.example.pusoydos

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pusoydos.Model.*
import java.lang.StringBuilder

/**
 * CardAdapter for displaying Cards
 */
class CardAdapter (val playerList: MutableList<Player>?, val cardList: List<Cards>?) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){ //List<Cards>?
    override fun getItemCount()=cardList!!.size

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        this.mContext=parent.context

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mCard = cardList!!.get(position)

        if (mCard.image != null) {
            Glide.with(mContext!!)
                .load(mCard.image)
                .into(holder.cardThumbnail)
        }
        if (mCard.code != null) {
            var cardFullName = mCard.value.plus(" of ").plus(mCard.suit)
            //println("cardfullname :$cardFullName")
            holder.cardName.setText(cardFullName)
        }

        when{
            playerList?.get(0)!!.playerHand.contains(mCard) -> holder.playerName.setText(playerList[0].name)
            playerList?.get(1)!!.playerHand.contains(mCard) -> holder.playerName.setText(playerList[1].name)
            playerList?.get(2)!!.playerHand.contains(mCard) -> holder.playerName.setText(playerList[2].name)
            playerList?.get(3)!!.playerHand.contains(mCard) -> holder.playerName.setText(playerList[3].name)
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val cardThumbnail: ImageView = itemView.findViewById(R.id.card_imageview)
        val playerName: TextView = itemView.findViewById(R.id.player_name_textview)
        val cardName:TextView = itemView.findViewById(R.id.card_name_textview)
    }
}