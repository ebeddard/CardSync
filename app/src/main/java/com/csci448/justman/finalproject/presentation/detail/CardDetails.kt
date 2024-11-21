package com.csci448.justman.finalproject.presentation.detail


import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.justman.finalproject.R
import com.csci448.justman.finalproject.data.model.Card


private const val LOG_TAG = "448.CardDetail"


@Composable
fun CardDetail(card: Card, onChangeBalance: (String) -> Unit, onNotify: () -> Unit) {
    val imageSize = with(LocalDensity.current) { 384.toDp() }

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            CardDetailLandscape(card = card, imageSize = imageSize,
                onChangeBalance = { onChangeBalance(it)} ) {
                onNotify()
            }
        }
        else -> {
            CardDetailPortrait(card = card, imageSize = imageSize,
                onChangeBalance = { onChangeBalance(it)} ) {
                onNotify()
            }
        }
    }
}

@Composable
fun CardDetailLandscape(modifier: Modifier = Modifier, card: Card, imageSize: Dp,
                        onChangeBalance: (String) -> Unit, onNotify: () -> Unit) {

    Row(Modifier.padding(16.dp)) {
        // CardDetailImage(imageSize = imageSize, card = card, modifier = Modifier.weight(1f))
        CardDetailItems(card = card, modifier = Modifier.weight(0.25f),
            onChangeBalance = { onChangeBalance(it)} ) {
            onNotify()
        }
    }

}

@Composable
fun CardDetailPortrait(modifier: Modifier = Modifier, card: Card, imageSize: Dp,
                       onChangeBalance: (String) -> Unit, onNotify: () -> Unit) {

    Column(Modifier.padding(16.dp)) {
        //CardDetailImage(imageSize = imageSize, card = card)
        CardDetailItems(card = card,
            onChangeBalance = { onChangeBalance(it)} ) {
            onNotify()
        }
    }

}

@Composable
fun CardDetailItems(modifier: Modifier = Modifier, card: Card,
                    onChangeBalance: (String) -> Unit, onNotify: () -> Unit) {

    var balance = remember {
        mutableStateOf(card.balance)
    }
    Log.d(LOG_TAG, balance.value)
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            CardDetailItem(
                label = stringResource(id = R.string.detail_card_number),
                value = card.cardNumber
            )
            card.securityNumber?.let {
                CardDetailItem(
                    label = stringResource(id = R.string.detail_security_number),
                    value = it
                )
            }

            card.expirationDate?.let {
                CardDetailItem(
                    label = stringResource(id = R.string.detail_expiration_date),
                    value = it
                )
            }


            Row(modifier = modifier.padding(top = 16.dp)) {
                TextField(
                    value = balance.value,
                    onValueChange = { balance.value = it },
                    label = { Text(text = stringResource(id = R.string.detail_balance)) },
                    placeholder = { Text(text = "0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Row {
               Button(onClick = {
                   onChangeBalance(balance.value)
                   // add checkbox for notification?
                   onNotify()
               }) {
                   Text("Update")
               }
            }
        }
    }
}

@Composable
fun CardDetailItem(modifier: Modifier = Modifier, label: String, value: String) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CardDetailImage(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    card: Card,
    photoId: Int? = card.photoID
) {
    if (photoId?.let { painterResource(id = it) } != null) {
        Box(
            modifier = modifier
                .width(imageSize)
                .height(imageSize)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Log.d(LOG_TAG, "Displaying Card \"${photoId}\"")
            Image(
                painter = painterResource(id = photoId),
                contentDescription = photoId.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

