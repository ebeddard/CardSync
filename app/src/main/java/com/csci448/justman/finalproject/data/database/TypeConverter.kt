package com.csci448.justman.finalproject.data.database

import androidx.room.TypeConverter
import java.util.*

class ProjectTypeConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
    @TypeConverter
    fun toUUID(uuid: String?) = UUID.fromString(uuid)
}