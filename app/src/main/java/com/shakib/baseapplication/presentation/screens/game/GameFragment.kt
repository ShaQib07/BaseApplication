package com.shakib.baseapplication.presentation.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
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

        viewModel.fetchFavoriteGames()
            .observe(viewLifecycleOwner, { gamesAdapter.submitFavList(it) })
    }
}