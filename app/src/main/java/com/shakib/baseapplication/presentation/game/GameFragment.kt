package com.shakib.baseapplication.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGameBinding.inflate(layoutInflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        viewModel.fetchGameList()
        viewModel.gameListLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> viewModel.showProgress()
                is Resource.Success -> {
                    lifecycleScope.launch { configureRecyclerView(response.data, viewModel.fetchFavGameList()) }
                }
                is Resource.Error -> {
                    lifecycleScope.launch { configureRecyclerView(listOf(), listOf()) }
                }
            }
        })
    }

    private fun configureRecyclerView(games: List<Game>, favGames: List<Game>) {
        viewModel.hideProgress()
        if (games.isNullOrEmpty())
            context?.showLongToast("Oops!! Something went wrong...")
        else
            binding.rvGames.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = GameAdapter(
                    games,
                    favGames,
                    { game -> context.showLongToast(game?.name.toString()) },
                    { game, isFavorite ->
                        if (isFavorite)
                            viewModel.addToFavorite(game)
                        else
                            viewModel.removeFromFavorite(game)
                    })
            }
    }
}