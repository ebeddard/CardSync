package com.csci448.justman.finalproject.presentation.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.newitems.NewBrandScreen
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel

object NewBrandScreenSpec: IScreenSpec {
    private const val LOG_TAG = "448.NewCardScreenSpec"

    override val route = "newBrand"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?): String = route

    override val title: Int
        get() = R.string.app_name

    @Composable
    override fun Content(
        alarmReceiver: CardAlarmReceiver,
        cardHolderViewModel: CardHolderViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {
        NewBrandScreen(){
            val brand = Brand(it)
            cardHolderViewModel.addBrand(brand)
            navController.popBackStack()
            Log.d(LOG_TAG, "adding brand")
        }
    }

    @Composable
    override fun TopAppBarActions(
        cardHolderViewModel: CardHolderViewModel,
        navHostController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {

    }

}