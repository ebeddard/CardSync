package com.csci448.justman.finalproject.presentation.navigation.specs

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel

sealed interface IScreenSpec {
    companion object {
        private const val LOG_TAG = "448.IScreenSpec"

        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            Log.d(LOG_TAG, "allScreens: mapping route \"${it.objectInstance?.route ?: ""}\" to object \"${it.objectInstance}\"")
            it.objectInstance?.route to it.objectInstance
        }
        const val root = "CardHolder"
        val startDestination = BrandListScreenSpec.route

        @Composable
        fun TopBar(cardHolderViewModel: CardHolderViewModel, navHostController: NavHostController, navBackStackEntry: NavBackStackEntry?, context: Context){
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.TopAppBarContent(cardHolderViewModel, navHostController, context, navBackStackEntry)
        }
    }

    val route: String
    val arguments: List<NamedNavArgument>
    val title: Int

    fun buildRoute(vararg args: String?): String

    @Composable
    fun Content(
        alarmReceiver: CardAlarmReceiver,
        cardHolderViewModel: CardHolderViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        context: Context
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBarContent(cardHolderViewModel: CardHolderViewModel, navHostController: NavHostController, context: Context, navBackStackEntry: NavBackStackEntry?){
        TopAppBar(
            navigationIcon = if (navHostController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.menu_back_desc)
                        )
                    }
                }
            } else {
                { }
            },
            title = { Text(text = stringResource(id = title)) },
            actions = {
                TopAppBarActions(
                    cardHolderViewModel = cardHolderViewModel,
                    navHostController = navHostController,
                    navBackStackEntry = navBackStackEntry,
                    context = context
                )
            }
        )
    }

    @Composable
    abstract fun TopAppBarActions(cardHolderViewModel: CardHolderViewModel, navHostController: NavHostController, navBackStackEntry: NavBackStackEntry?, context: Context)

}