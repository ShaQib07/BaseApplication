package com.shakib.baseapplication.presentation.screens.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.R
import com.shakib.baseapplication.common.extensions.loadImageFromUrl
import com.shakib.baseapplication.common.utils.DiffUtilCallBack
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.ItemGameBinding

class GamesAdapter(
    private val clickListener: (Game?) -> Unit,
    private val favoriteListener: (Game?, isFavorite: Boolean) -> Unit
) : PagingDataAdapter<Game, GamesAdapter.GamesViewHolder>(DiffUtilCallBack()) {

    private val favGameList: ArrayList<Game> = ArrayList()

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        getItem(position)?.let { game ->
            holder.binding.ivGame.loadImageFromUrl(game.backgroundImage)
            holder.binding.tvName.text = game.name
            holder.binding.ivFav.apply {
                tag = if (favGameList.contains(game)) {
                    setImageResource(R.drawable.ic_fav_active)
                    "active"
                } else {
                    setImageResource(R.drawable.ic_fav_inactive)
                    "inactive"
                }
                setOnClickListener {
                    tag = if (this.tag.equals("inactive")) {
                        setImageResource(R.drawable.ic_fav_active)
                        favoriteListener.invoke(game, true)
                        "active"
                    } else {
                        setImageResource(R.drawable.ic_fav_inactive)
                        favoriteListener.invoke(game, false)
                        "inactive"
                    }
                }
            }
            holder.itemView.setOnClickListener { clickListener.invoke(game) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GamesViewHolder(
        ItemGameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun submitFavList(favGameList: List<Game>) {
        this.favGameList.clear()
        this.favGameList.addAll(favGameList)
        notifyDataSetChanged()
    }

    class GamesViewHolder(val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root)
}