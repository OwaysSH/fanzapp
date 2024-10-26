package com.fanz.oways.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fanz.oways.R
import com.fanz.oways.databinding.PlayerCardLargeItemBinding
import com.fanz.oways.databinding.PlayerItemBinding
import com.fanz.oways.databinding.PlayerLargeItemBinding
import com.fanz.oways.databinding.PlayerSelectionPlaceholderBinding
import com.fanz.oways.model.Player
import com.fanz.oways.model.PositionType
import kotlin.random.Random


interface PlayerSelectionListener {
    fun select(position: Int, player: Player)

}

class PlayersAdapter(
    val playerSelectionListener: PlayerSelectionListener,
    var selectedPlayers: HashMap<String, Player> = hashMapOf()
) : RecyclerView.Adapter<ViewHolder>() {

    private var teamCompeted: Boolean? = false
    private var players: ArrayList<Player> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            PlayerLargeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PlayerLargeCardViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        (holder as PlayerLargeCardViewHolder).bind(players[position])
    }

    fun addList(list: List<Player>) {
        players = list as ArrayList<Player>
        notifyDataSetChanged()
    }

    fun teamIsComplete(completed: Boolean) {
        teamCompeted = completed
    }

    inner class PlayerLargeCardViewHolder(private val binding: PlayerLargeItemBinding) :
        ViewHolder(binding.root) {

        fun bind(item: Player) {
            binding.apply {
                playerNameTv.text = item.name
                playerNumberTv.text =
                    String.format(this.root.context.getString(R.string.player_number), item.number)
                playerCountryNameTv.text = item.nationality
                playerPositionTv.text = item.position
                cardTypeTv.text = item.cardType

                playerPriceTv.text = item.price

                val isSelected = selectedPlayers.containsKey(item.club_code.plus(item.number))
                playerSelectionLayout(isSelected)

                root.setOnClickListener {
                    val isSelected = selectedPlayers.containsKey(item.club_code.plus(item.number))
                    if (!isSelected && teamCompeted == false) {
                        playerSelectionLayout(!isSelected)
                        playerSelectionListener.select(layoutPosition, item)
                        selectedPlayers[item.club_code.plus(item.number)] = item
                        notifyItemChanged(layoutPosition)
                    }
                }
            }
        }

        private fun PlayerLargeItemBinding.playerSelectionLayout(isSelected: Boolean) {
            selectedLayout.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }
}


