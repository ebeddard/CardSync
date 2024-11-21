package com.csci448.justman.finalproject.presentation.list.brandlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.csci448.justman.finalproject.data.model.Brand
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandListItem(brand: Brand, cardNumber: Int, brandTotal: Double, onSelectBrand: (Brand) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onSelectBrand(brand) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)) {
                Column(modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.Start) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = brand.name,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                }
                Column(modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.End) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "Amount:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "$%.2f".format(brandTotal),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)) {
                Column() {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "Number of Cards: $cardNumber",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBrandListItem() {
    val brand = Brand("Test")
    BrandListItem(brand = brand, 0, 0.0 , onSelectBrand = {})
}