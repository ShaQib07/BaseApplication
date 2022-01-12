package com.shakib.baseapplication.presentation.screens.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.extensions.loadImageFromUrl
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.ItemGameBinding

class GameAdapter(
    private val clickListener: (Game?) -> Unit,
    private val favoriteListener: (Game?, size: Int) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val favGameList: ArrayList<Game> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameViewHolder(
        ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.binding.ivGame.loadImageFromUrl(favGameList[position].backgroundImage)
        holder.binding.tvName.text = favGameList[position].name
        holder.binding.ivFav.apply {
            setImageResource(R.drawable.ic_fav_active)
            setOnClickListener { removeFromFavorite(position) }
        }
        holder.itemView.setOnClickListener { clickListener.invoke(favGameList[position]) }
    }

    private fun removeFromFavorite(position: Int) {
        favoriteListener.invoke(favGameList[position], favGameList.size-1)
        favGameList.remove(favGameList[position])
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favGameList.size)
    }

    override fun getItemCount() = favGameList.size

    fun submitList(favGameList: List<Game>) {
        this.favGameList.clear()
        this.favGameList.addAll(favGameList)
        notifyDataSetChanged()
    }

    class GameViewHolder(val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root)
}