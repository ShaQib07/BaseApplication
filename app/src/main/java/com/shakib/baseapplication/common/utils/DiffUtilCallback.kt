package com.shakib.baseapplication.common.utils

import androidx.recyclerview.widget.DiffUtil
import com.shakib.baseapplication.data.model.Game

class DiffUtilCallBack : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.backgroundImage == newItem.backgroundImage
    }
}