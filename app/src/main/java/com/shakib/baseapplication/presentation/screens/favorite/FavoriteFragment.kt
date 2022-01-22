package com.shakib.baseapplication.presentation.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.*
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.databinding.FragmentFavoriteBinding
import com.shakib.baseapplication.presentation.screens.game.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var gameAdapter: GameAdapter

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        gameAdapter = GameAdapter(
            { game -> context?.showLongToast(game?.name.toString()) },
            { game, size ->
                viewModel.removeFromFavorite(game)
                if (size == 0) binding.tvEmpty.visible()
            })
        binding.rvFavGames.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = gameAdapter
        }
    }

    override fun bindWithViewModel() {
        super.bindWithViewModel()
        viewModel.fetchFavoriteGames()
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.favGamesStateFlow.collect {
                    // New value received
                    when (it) {
                        is Resource.Loading -> printDebugLog("Show Loading")
                        is Resource.Success -> {
                            if (it.data.isNullOrEmpty())
                                binding.tvEmpty.visible()
                            else {
                                binding.tvEmpty.invisible()
                                gameAdapter.submitList(it.data)
                            }
                        }
                        is Resource.Error -> printErrorLog(it.throwable.message.toString())
                    }
                }
            }
        }
    }
}