package com.csci448.justman.finalproject.presentation.navigation.specs

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.*
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.presentation.list.cardlist.CardListScreen
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel


object CardListScreenSpec: IScreenSpec {
    private const val LOG_TAG = "448.CardListScreenSpec"

    private const val ROUTE_BASE = "cardlist"
    private const val ARG_UUID_NAME = "uuid"

    private fun buildFullRoute(argVal: String): String {
        var fullRoute = ROUTE_BASE
        if(argVal == ARG_UUID_NAME) {
            fullRoute += "/{$argVal}"
            Log.d(LOG_TAG, "Built base route $fullRoute")
        } else {
            fullRoute += "/$argVal"
            Log.d(LOG_TAG, "Built specific route $fullRoute")
        }
        return fullRoute
    }

    override val title = R.string.app_name
    override val route = buildFullRoute(ARG_UUID_NAME)

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ARG_UUID_NAME) {
            type = NavType.StringType
        }
    )

    override fun buildRoute(vararg args: String?): String = buildFullRoute(args[0] ?: "0")

    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    override fun Content(
        alarmReceiver: CardAlarmReceiver,
        cardHolderViewModel: CardHolderViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    ) {
        val cardList = cardHolderViewModel.cardListState.collectAsState().value
        val brand = cardHolderViewModel.currentBrandState.value
        CardListScreen(cardHolderViewModel, cardList, brand) {
            val uuid = it.id
            cardHolderViewModel.loadCardByID(uuid)
            if (uuid != null) {
                cardHolderViewModel.loadCardByID(uuid)
                val navTo = "detail/{$uuid}"
                navController.navigate(navTo)
            }
        }
    }

    @Composable
    override fun TopAppBarActions(
        cardHolderViewModel: CardHolderViewModel,
        navHostController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
            IconButton(onClick = { navHostController.navigate(NewCardScreenSpec.route) }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(R.string.add_card)
                )
            }
    }
}