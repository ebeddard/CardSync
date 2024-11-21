package com.csci448.justman.finalproject.presentation.list.cardlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card
import com.csci448.justman.finalproject.presentation.list.brandlist.BrandListItem
import com.csci448.justman.finalproject.presentation.viewmodel.CardHolderViewModel
import kotlinx.coroutines.flow.toList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(cardHolderViewModel: CardHolderViewModel, cardList: List<Card>, brand: Brand?,
                   onClick: (Card) -> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cardList) { card ->
            val dismissState = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToStart){
                        cardHolderViewModel.deleteCard(card)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> Color.Black
                            DismissValue.DismissedToStart -> Color.Red
                            else -> Color.White
                        }
                    )
                    val scale by animateFloatAsState(targetValue = if(dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(12.dp),
                    contentAlignment = Alignment.CenterEnd){
                        Icon(Icons.Default.Delete, contentDescription = "Delete Swipe", modifier = Modifier.scale(scale), tint = color)
                    }         
                },
                dismissContent = {
                    CardListItem(card = card, brand = brand, onSelectCard = onClick)}
                )
        }
    }
}