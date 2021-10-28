package com.shakib.baseapplication.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.common.extensions.loadImageFromUrl
import com.shakib.baseapplication.data.model.ScreenShot
import com.shakib.baseapplication.databinding.ItemScreenShotBinding

class ImageSliderAdapter(private val imageList: List<ScreenShot>) :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScreenShotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivSs.loadImageFromUrl(imageList[position].image)
    }

    override fun getItemCount() = imageList.size

    inner class ViewHolder(val binding: ItemScreenShotBinding) :
        RecyclerView.ViewHolder(binding.root)
}