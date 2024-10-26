package com.fanz.oways.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fanz.oways.R
import com.fanz.oways.databinding.PlayerItemBinding
import com.fanz.oways.databinding.PlayerSelectionPlaceholderBinding
import com.fanz.oways.model.LinkedList
import com.fanz.oways.model.Player
import com.fanz.oways.model.PlayerSelectedItem
import com.fanz.oways.model.PositionType

enum class SelectedCardType {
    PLAYER,
    PLACEHOLDER
}

interface EmptyCardPositionSelectionListener {
    fun select(position: Int, positionType: PositionType)
    fun completeSelection(isCompleted: Boolean)
}

class SelectedPlayersAdapter(
    val itemWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    val emptyCardSelectionListener: EmptyCardPositionSelectionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPosition: Int = 0
    private var selectionList: LinkedList<PlayerSelectedItem> = LinkedList()

    init {
        selectionList = LinkedList()
        selectionList.add(PlayerSelectedItem(PositionType.GK, null))
        selectionList.add(PlayerSelectedItem(PositionType.DEF, null))
        selectionList.add(PlayerSelectedItem(PositionType.DEF, null))
        selectionList.add(PlayerSelectedItem(PositionType.DEF, null))
        selectionList.add(PlayerSelectedItem(PositionType.DEF, null))
        selectionList.add(PlayerSelectedItem(PositionType.DEF, null))
        selectionList.add(PlayerSelectedItem(PositionType.MID, null))
        selectionList.add(PlayerSelectedItem(PositionType.MID, null))
        selectionList.add(PlayerSelectedItem(PositionType.MID, null))
        selectionList.add(PlayerSelectedItem(PositionType.MID, null))
        selectionList.add(PlayerSelectedItem(PositionType.MID, null))
        selectionList.add(PlayerSelectedItem(PositionType.FWD, null))
        selectionList.add(PlayerSelectedItem(PositionType.FWD, null))
        selectionList.add(PlayerSelectedItem(PositionType.FWD, null))
        selectionList.add(PlayerSelectedItem(PositionType.FWD, null))
        emptyCardSelectionListener.select(0, selectionList.getAt(0)!!.positionCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            SelectedCardType.PLAYER.ordinal -> {
                val binding =
                    PlayerItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                binding.root.layoutParams =
                    RecyclerView.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                (binding.root.layoutParams as RecyclerView.LayoutParams).marginStart =
                    parent.resources.getDimensionPixelSize(R.dimen.dp_8)
                (binding.root.layoutParams as RecyclerView.LayoutParams).marginEnd =
                    parent.resources.getDimensionPixelSize(R.dimen.dp_8)
                PlayerViewHolder(binding)
            }

            else -> {
                val binding =
                    PlayerSelectionPlaceholderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                binding.root.layoutParams =
                    RecyclerView.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                /*(binding.root.layoutParams as RecyclerView.LayoutParams).marginStart =
                    parent.resources.getDimensionPixelSize(R.dimen.dp_8)
                (binding.root.layoutParams as RecyclerView.LayoutParams).marginEnd =
                    parent.resources.getDimensionPixelSize(R.dimen.dp_8)*/
                PlayerPlaceHolderViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (selectionList.getAt(position)?.player == null) {
            SelectedCardType.PLACEHOLDER.ordinal
        } else {
            SelectedCardType.PLAYER.ordinal

        }
    }

    override fun getItemCount(): Int {
        println("##SELECTION_SIZE size = ${selectionList.size()}")
        return selectionList.size()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            SelectedCardType.PLAYER.ordinal -> {
                selectionList.getAt(position)?.player?.let { (holder as PlayerViewHolder).bind(it, selectionList.getAt(position)) }
            }

            SelectedCardType.PLACEHOLDER.ordinal -> {
                selectionList.getAt(position)
                    ?.let { (holder as PlayerPlaceHolderViewHolder).bind(it) }

            }
        }
    }

    fun setSelectionAt(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position

        if (itemCount > 0) {
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
        }

    }

    fun setSelectionPlayer(selectedPlayer: Player){
        selectionList.getAt(selectedPosition)?.apply {
            player = selectedPlayer
            notifyItemChanged(selectedPosition)
            val findNext = selectionList.findNext(selectedPosition)
            if (findNext>-1 && findNext<selectionList.size()){
                emptyCardSelectionListener.select(findNext, selectionList.getAt(findNext)!!.positionCode)
                emptyCardSelectionListener.completeSelection(false)

            }else{
                emptyCardSelectionListener.completeSelection(true)
            }
        }
    }

    inner class PlayerViewHolder(private val binding: PlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isSelected: Boolean = false
        fun bind(item: Player, selection: PlayerSelectedItem?) {
            binding.apply {
                playerNameTv.text = item.name
                playerNumberTv.text =
                    String.format(this.root.context.getString(R.string.player_number), item.number)
                playerCountryNameTv.text = item.nationality
                playerPositionTv.text = item.position
                cardTypeTv.text = item.cardType

                playerPriceTv.text = item.price
                playerSelectedPositionTv.text = selection?.positionCode?.name?:""

                root.setOnClickListener {
                    isSelected = !isSelected

                }
            }
        }
    }

    inner class PlayerPlaceHolderViewHolder(private val binding: PlayerSelectionPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayerSelectedItem) {
            binding.apply {
                positionTv.text = item.positionCode.name
                selection()
                root.setOnClickListener {
                    val isSelected = backgroundImage.isSelected
                    if (!isSelected) {
                        emptyCardSelectionListener.select(layoutPosition, item.positionCode)
                    }
                }
            }
        }

        private fun PlayerSelectionPlaceholderBinding.selection() {
            backgroundImage.isSelected = selectedPosition == layoutPosition
            positionTv.isSelected = selectedPosition == layoutPosition

        }


    }
}

