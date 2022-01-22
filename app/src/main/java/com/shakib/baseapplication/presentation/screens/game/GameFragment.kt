package com.shakib.baseapplication.presentation.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.printDebugLog
import com.shakib.baseapplication.common.extensions.printErrorLog
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var gamesAdapter: GamesAdapter

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGameBinding.inflate(layoutInflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)
        configureRecyclerView()
        viewModel.fetchFavoriteGames()
    }

    private fun configureRecyclerView() {
        gamesAdapter = GamesAdapter(
            { game ->
                screenNavigator.toDetailFragment(
                    findNavController(),
                    game?.name.toString(),
                    game?.id.toString()
                )
            },
            { game, isFavorite ->
                if (isFavorite)
                    viewModel.addToFavorite(game)
                else
                    viewModel.removeFromFavorite(game)
            })

        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gamesAdapter
            adapter = gamesAdapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { gamesAdapter.retry() },
                footer = LoadingAdapter { gamesAdapter.retry() }
            )
            gamesAdapter.addLoadStateListener {
                if (it.refresh is LoadState.Loading)
                    viewModel.showProgress()
                else
                    viewModel.hideProgress()
            }
        }

        lifecycleScope.launch {
            viewModel.paginatedGames.collectLatest { pagingData ->
                gamesAdapter.submitData(pagingData)
            }
        }

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
                        is Resource.Success -> gamesAdapter.submitFavList(it.data)
                        is Resource.Error -> printErrorLog(it.throwable.message.toString())
                    }
                }
            }
        }
    }
}