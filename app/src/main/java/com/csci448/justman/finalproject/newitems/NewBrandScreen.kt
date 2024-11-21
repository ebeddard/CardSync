package com.csci448.justman.finalproject.newitems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf

@Composable
fun NewBrandScreen(onClick: (brandName: String) -> Unit){
    var brandName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = brandName,
            onValueChange = {brandName = it},
            label = { Text(text = "Brand Name") },
            placeholder = { Text(text = "Target") },
        )

        Button(
            onClick = { onClick(brandName) }
        ){
            Text(
                text = "Submit"
            )
        }
    }
}