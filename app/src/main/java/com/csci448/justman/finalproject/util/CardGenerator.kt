package com.csci448.justman.finalproject.util
/*
import com.csci448.justman.finalproject.data.model.Card
import kotlin.random.Random

object CardGenerator {
    private val cardNumberLength = 16
    private val securityNumberLength = 3
    private val expirationDateLength = 5

    private val photoIds = listOf(1, 2, 3)

    private fun generateRandomString(length: Int): String {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun generateRandomCard(): Card {
        val balance = Random.nextDouble(0.0, 10000.0).toString()
        val cardNumber = generateRandomString(cardNumberLength)
        val securityNumber = generateRandomString(securityNumberLength)
        val expirationDate = generateRandomString(expirationDateLength)
        val photoId = photoIds.random()
        return Card(balance, cardNumber, securityNumber, expirationDate, photoId)
    }
}
*/