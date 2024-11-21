package com.csci448.justman.finalproject.presentation.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.csci448.justman.finalproject.data.model.Card
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel

@Composable
fun CardDetailScreen(card: Card, onChangeBalance: (String) -> Unit, onNotify: () -> Unit){
    CardDetail(card = card, onChangeBalance = { onChangeBalance(it) }) {
        onNotify()
    }
}