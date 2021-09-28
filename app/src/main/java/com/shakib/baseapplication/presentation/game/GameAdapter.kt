package com.shakib.baseapplication.presentation.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.extensions.loadImageFromUrl
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.ItemGameBinding

class GameAdapter(
    private val gameList: List<Game>,
    private val favGameList: List<Game>,
    private val clickListener: (Game?) -> Unit,
    private val favoriteListener: (Game?, isFavorite: Boolean) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameViewHolder(
        ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.binding.ivGame.loadImageFromUrl(gameList[position].backgroundImage)
        holder.binding.tvName.text = gameList[position].name
        holder.binding.ivFav.apply {
            tag = if (favGameList.contains(gameList[position])) {
                setImageResource(R.drawable.ic_fav_active)
                "active"
            } else {
                setImageResource(R.drawable.ic_fav_inactive)
                "inactive"
            }
            setOnClickListener {
                tag = if (this.tag.equals("inactive")) {
                    setImageResource(R.drawable.ic_fav_active)
                    favoriteListener.invoke(gameList[position], true)
                    "active"
                } else {
                    setImageResource(R.drawable.ic_fav_inactive)
                    favoriteListener.invoke(gameList[position], false)
                    "inactive"
                }
            }
        }
        holder.itemView.setOnClickListener { clickListener.invoke(gameList[position]) }
    }

    override fun getItemCount() = gameList.size

    class GameViewHolder(val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root)
}