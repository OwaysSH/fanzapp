package com.fanz.oways.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fanz.oways.databinding.ClubFilterItemBinding
import com.fanz.oways.model.Club

class ClubFilterAdapter: RecyclerView.Adapter<ClubFilterAdapter.ClubViewHolder>() {


    private var clubList: ArrayList<Club> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(ClubFilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return clubList.size
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {

        holder.bind(clubList[position])
    }

    fun addList(list: List<Club>) {

        clubList = list as ArrayList<Club>
        notifyDataSetChanged()
    }


    inner class ClubViewHolder(private val binding: ClubFilterItemBinding): RecyclerView.ViewHolder(binding.root){

        private var isSelcted: Boolean = false
        fun bind(club: Club) {
            binding.clubNameTv.text = club.name
            binding.rootLayout.isSelected = isSelcted

            binding.rootLayout.setOnClickListener {
                isSelcted = !isSelcted
                binding.rootLayout.isSelected = isSelcted

            }
        }

    }
}