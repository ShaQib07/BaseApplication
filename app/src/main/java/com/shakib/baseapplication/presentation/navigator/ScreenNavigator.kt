package com.shakib.baseapplication.presentation.navigator

import androidx.navigation.NavController
import com.shakib.baseapplication.presentation.MenuItemFragmentDirections
import com.shakib.baseapplication.presentation.ToolbarFragmentDirections
import javax.inject.Inject

class ScreenNavigator @Inject constructor() {

    fun toMenuFragment(navController: NavController, data: String) =
        navController.navigate(MenuItemFragmentDirections.primaryToMenuItem(data))

    fun toToolbarFragment(navController: NavController, data: String) =
        navController.navigate(ToolbarFragmentDirections.primaryToToolbar(data))
}