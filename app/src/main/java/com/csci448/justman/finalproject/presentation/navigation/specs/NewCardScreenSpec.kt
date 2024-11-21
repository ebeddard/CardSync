package com.csci448.justman.finalproject.presentation.navigation.specs

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.data.model.Card
import com.csci448.justman.finalproject.newitems.newcard.NewCardScreen
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel



object NewCardScreenSpec : IScreenSpec {
    private const val LOG_TAG = "448.NewCardScreenSpec"

    override val route = "newCard"
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
        context: Context) {

        val mediaPlayer = MediaPlayer.create(context, R.raw.register)

        NewCardScreen() {
            balance, cardNumber, security, expiration ->
            try{
                balance.toDouble()
                cardNumber.toInt()
                security.toInt()
                if (balance.isEmpty() || cardNumber.isEmpty() || security.isEmpty()){
                    throw NumberFormatException()
                }
                val card = cardHolderViewModel.currentBrandState.value?.let { Card(balance, cardNumber, security, expiration, null, it.id) }
                if (card != null) {
                    cardHolderViewModel.addCard(card)
                    alarmReceiver.checkPermissionAndScheduleAlarm(context as Activity)
                    mediaPlayer.start()
                    navController.popBackStack()
                }
            }
            catch (e: NumberFormatException){
                Toast.makeText(context, "Invalid Input Value", Toast.LENGTH_SHORT).show()
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

    }
}