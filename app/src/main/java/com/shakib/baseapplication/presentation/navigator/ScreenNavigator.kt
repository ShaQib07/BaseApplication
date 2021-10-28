package com.shakib.baseapplication.presentation.navigator

import androidx.navigation.NavController
import com.shakib.baseapplication.presentation.MenuItemFragmentDirections
import com.shakib.baseapplication.presentation.ToolbarFragmentDirections
import com.shakib.baseapplication.presentation.details.DetailFragmentDirections
import com.shakib.baseapplication.presentation.game.GameFragmentDirections
import javax.inject.Inject

class ScreenNavigator @Inject constructor() {

    fun toMenuFragment(navController: NavController, data: String) =
        navController.navigate(MenuItemFragmentDirections.mainToMenuItem(data))

    fun toRxFragment(navController: NavController, data: String) =
        navController.navigate(GameFragmentDirections.mainToRx(data))

    fun toFlowFragment(navController: NavController, data: String) =
        navController.navigate(GameFragmentDirections.mainToFlow(data))

    fun toToolbarFragment(navController: NavController, data: String) =
        navController.navigate(ToolbarFragmentDirections.mainToToolbar(data))

    fun toDetailFragment(navController: NavController, title: String, gameId: String) =
        navController.navigate(DetailFragmentDirections.mainToDetail(title, gameId))
}