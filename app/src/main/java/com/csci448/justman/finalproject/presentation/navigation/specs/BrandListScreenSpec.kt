package com.csci448.justman.finalproject.presentation.navigation.specs

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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.presentation.list.brandlist.BrandListScreen
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel
import kotlinx.coroutines.flow.collect

object BrandListScreenSpec : IScreenSpec {
    override val route = "brandlist"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

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

        val brands = cardHolderViewModel.brandListState.collectAsState()

        BrandListScreen(brands.value, cardHolderViewModel){
            val uuid = it.id
            cardHolderViewModel.loadBrandByID(uuid)
            navController.navigate(CardListScreenSpec.route)
        }
    }

    @Composable
    override fun TopAppBarActions(
        cardHolderViewModel: CardHolderViewModel,
        navHostController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {


            IconButton(onClick = { navHostController.navigate(NewBrandScreenSpec.route) }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(R.string.add_brand)
                )
            }

    }
}