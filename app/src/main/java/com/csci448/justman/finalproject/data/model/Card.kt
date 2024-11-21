package com.csci448.justman.finalproject.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "cards",foreignKeys = arrayOf(ForeignKey(entity = Brand::class, parentColumns = arrayOf("id"), childColumns = arrayOf("brandID"), onDelete = ForeignKey.CASCADE)))
data class Card (
    val balance: String,
    val cardNumber: String,
    val securityNumber: String?,
    val expirationDate: String?,
    val photoID: Int?,
    @ColumnInfo(name = "brandID")
    val brandID: UUID,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID = UUID.randomUUID()
)