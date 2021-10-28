package com.shakib.baseapplication.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.extensions.visible
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.databinding.FragmentFavoriteBinding
import com.shakib.baseapplication.presentation.game.GameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun getBaseViewModel() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)

    override fun configureViews(savedInstanceState: Bundle?) {
        super.configureViews(savedInstanceState)

        viewModel.fetchFavGameList()
        viewModel.favGameListLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                //is Resource.Loading -> viewModel.showProgress()
                is Resource.Success -> configureRecyclerView(response.data)
                is Resource.Error -> configureRecyclerView(listOf())
            }
        })
    }

    private fun configureRecyclerView(games: List<Game>) {
        viewModel.hideProgress()
        if (games.isNullOrEmpty())
            binding.tvEmpty.visible()
        else
            binding.rvFavGames.apply {
                visible()
                layoutManager = GridLayoutManager(context, 2)
                adapter = GameAdapter(
                    games,
                    games,
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