package com.csci448.justman.finalproject.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.csci448.justman.finalproject.presentation.navigation.specs.IScreenSpec
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel


@Composable
fun CardHolderTopBar(navHostController: NavHostController, cardHolderViewModel: CardHolderViewModel, context: Context){
    val navBackStackEntryState = navHostController.currentBackStackEntryAsState()
    IScreenSpec.TopBar(cardHolderViewModel, navHostController, navBackStackEntryState.value, context)
}