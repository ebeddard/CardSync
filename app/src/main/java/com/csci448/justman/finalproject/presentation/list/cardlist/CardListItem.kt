package com.csci448.justman.finalproject.presentation.list.cardlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(card: Card, brand: Brand?, onSelectCard: (Card) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onSelectCard(card) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)) {
                Column(modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.Start) {
                    if (brand != null) {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = brand.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 24.sp
                        )
                    }
                }
                Column(modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.End) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "Amount:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp
                    )
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "$" + card.balance,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                }
            }
            if(card.expirationDate != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    Column() {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = "Expiration:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    }
                    Column() {
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = card.expirationDate,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCardListItemWithExpiration() {
    /*val card = Card("25.00",  "1234", "567","7/1/23", 0)
    CardListItem(card = card, onSelectCard = {})*/
}

@Preview
@Composable
fun PreviewCardListItemWithoutExpiration() {
    /*val card = Card("25.00",  "1234", "567",null, 0)
    CardListItem(card = card, onSelectCard = {})*/
}