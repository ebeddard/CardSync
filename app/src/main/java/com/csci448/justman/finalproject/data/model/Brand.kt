package com.csci448.justman.finalproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "brands")
data class Brand(
    val name: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID = UUID.randomUUID()
 )