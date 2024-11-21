package com.csci448.justman.finalproject.presentation.navigation.specs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.*
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.presentation.detail.CardDetailScreen
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel


object CardDetailScreenSpec : IScreenSpec {
    private const val LOG_TAG = "448.CardDetailScreenSpec"

    private const val ROUTE_BASE = "detail"
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
        val mediaPlayer = MediaPlayer.create(context, R.raw.register)
        val card = cardHolderViewModel.currentCardState.collectAsState()
        Log.d(LOG_TAG, card.toString())
        card.value?.let { card -> CardDetailScreen(card, onChangeBalance =  { balance ->
            try{
                balance.toDouble()
                cardHolderViewModel.updateBalance(balance)
                mediaPlayer.start()
                navController.popBackStack()
            }
            catch (e: NumberFormatException){
                Toast.makeText(context, "Invalid Balance Value", Toast.LENGTH_SHORT).show()
            }
        }) {
            alarmReceiver.checkPermissionAndScheduleAlarm(context as Activity)
        } }
    }

    @Composable
    override fun TopAppBarActions(
        cardHolderViewModel: CardHolderViewModel,
        navHostController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        IconButton(onClick = { cardHolderViewModel.deleteCard(cardHolderViewModel.currentCardState.value); navHostController.popBackStack() }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "delete card"
            )
        }

    }
}