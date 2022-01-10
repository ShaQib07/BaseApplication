package com.shakib.baseapplication.presentation.screens.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakib.baseapplication.databinding.ItemLoadingStateBinding

class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadingStateViewHolder(
        ItemLoadingStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            if (loadState is LoadState.Error) {
                tvErrorMessage.text = loadState.error.localizedMessage
            }
            // 4
            progressBar.isVisible = loadState is LoadState.Loading
            tvErrorMessage.isVisible = loadState !is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading

            btnRetry.setOnClickListener {
                retry()
            }
        }

    }

    class LoadingStateViewHolder(val binding: ItemLoadingStateBinding) :
        RecyclerView.ViewHolder(binding.root)
}