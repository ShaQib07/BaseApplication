package com.shakib.baseapplication.presentation.navigator

import androidx.navigation.NavController
import com.shakib.baseapplication.presentation.dialog.ProgressDialogDirections
import com.shakib.baseapplication.presentation.dialog.SimpleDialogDirections
import javax.inject.Inject

class DialogNavigator @Inject constructor() {

    fun toSimpleDialog(navController: NavController, title: String, msg: String) =
        navController.navigate(SimpleDialogDirections.toolbarToDialog(title, msg))

    fun showProgress(navController: NavController) =
        navController.navigate(ProgressDialogDirections.showProgress())
}