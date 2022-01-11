package com.shakib.baseapplication.presentation.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        viewModel.favoriteGames.observe(viewLifecycleOwner, { configureRecyclerView(it) })
    }

    private fun configureRecyclerView(favGameList: List<Game>) {
        gamesAdapter = GamesAdapter(
            favGameList,
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
        }

        lifecycleScope.launch {
            viewModel.paginatedGames.collectLatest { pagingData ->
                gamesAdapter.submitData(pagingData)
            }
        }
    }
}