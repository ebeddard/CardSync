package com.csci448.justman.finalproject

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SettingsSlicesContract.BASE_URI
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.csci448.justman.finalproject.alarm.CardAlarmReceiver
import com.csci448.justman.finalproject.presentation.list.brandlist.BrandListScreen
import com.csci448.justman.finalproject.presentation.navigation.CardHolderNavHost
import com.csci448.justman.finalproject.presentation.navigation.CardHolderTopBar
import com.csci448.justman.finalproject.presentation.navigation.specs.CardListScreenSpec
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModelFactory
import com.csci448.justman.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {

    private  lateinit var mCardHolderViewModel: CardHolderViewModel
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    companion object {
        private var cardAlarmReceiver = CardAlarmReceiver()
        fun createPendingIntent(context: Context):
                PendingIntent {
            val deepLinkIntent = Intent(
                Intent.ACTION_VIEW,
                formatUriString().toUri(),
                context,
                MainActivity::class.java
            )
            return TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(deepLinkIntent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
        }

        private fun formatUriString(): String {
            var route = CardListScreenSpec.route
            val uriStringBuilder = StringBuilder()
            uriStringBuilder.append(BASE_URI)
            uriStringBuilder.append("/$route/")
            uriStringBuilder.append("/")
            return uriStringBuilder.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CardHolderViewModelFactory(this)
        mCardHolderViewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                cardAlarmReceiver.checkPermissionAndScheduleAlarm(
                    this@MainActivity,
                    //permissionLauncher
                )
            }


        setContent {
            MainActivityContent(cardHolderViewModel = mCardHolderViewModel)
        }
    }
    @Composable
    private fun MainActivityContent(cardHolderViewModel: CardHolderViewModel) {
        val navController = rememberNavController()
        val context = LocalContext.current

        FinalProjectTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = {
                        CardHolderTopBar(
                            navHostController = navController,
                            cardHolderViewModel = cardHolderViewModel,
                            context = context
                        )
                    }
                ){
                        contentPadding -> CardHolderNavHost(alarmReceiver = cardAlarmReceiver, navController = navController,
                    cardHolderViewModel = cardHolderViewModel, context = context, modifier = Modifier.padding(contentPadding))
                }
            }
        }
    }
}
