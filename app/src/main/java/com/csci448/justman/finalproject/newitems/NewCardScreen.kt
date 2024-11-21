package com.csci448.justman.finalproject.newitems.newcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.csci448.justman.finalproject.R
import kotlin.math.exp

@Composable
fun NewCardScreen(onClick: (balance: String, cardNumber: String, security: String, expiration: String) -> Unit){
    var balance by remember {
        mutableStateOf("")
    }

    var cardNumber by remember {
        mutableStateOf("")
    }

    var security by remember {
        mutableStateOf("")
    }

    var expiration by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = balance,
            onValueChange = { balance = it },
            label = { Text(text = stringResource(id = R.string.detail_balance))},
            placeholder = { Text(text = "$0.00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text(text = stringResource(id = R.string.detail_card_number))},
            placeholder = { Text(text = "0000000000000000") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = security,
            onValueChange = { security = it },
            label = { Text(text = stringResource(id = R.string.detail_security_number))},
            placeholder = { Text(text = "000") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = expiration,
            onValueChange = { expiration = it },
            label = { Text(text = stringResource(id = R.string.detail_expiration_date))},
            placeholder = { Text(text = "00/00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
                onClick = { onClick(balance, cardNumber, security, expiration) }
                ){
            Text(
                text = "Submit"
            )
        }
    }


}
