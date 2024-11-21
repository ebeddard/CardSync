package com.csci448.justman.finalproject.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.presentation.navigation.specs.IScreenSpec
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel


@Composable
fun CardHolderNavHost(alarmReceiver: CardAlarmReceiver,
                      modifier: Modifier = Modifier,
                      navController: NavHostController,
                      cardHolderViewModel: CardHolderViewModel,
                      context: Context
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = IScreenSpec.root
    ) {
        navigation(
            route = IScreenSpec.root,
            startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if(screen != null) {
                    composable(
                        route = screen.route,
                        arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            alarmReceiver = alarmReceiver,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry,
                            cardHolderViewModel = cardHolderViewModel,
                            context = context
                        )
                    }
                }
            }
        }
    }
}

