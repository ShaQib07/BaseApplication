package com.shakib.baseapplication.presentation.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.shakib.baseapplication.common.base.BaseFragment
import com.shakib.baseapplication.common.extensions.invisible
import com.shakib.baseapplication.common.extensions.showLongToast
import com.shakib.baseapplication.common.extensions.visible
import com.shakib.baseapplication.databinding.FragmentFavoriteBinding
import com.shakib.baseapplication.presentation.screens.game.GameAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.fetchFavoriteGames().observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty())
                binding.tvEmpty.visible()
            else {
                binding.tvEmpty.invisible()
                gameAdapter.submitList(it)
            }
        })
    }
}