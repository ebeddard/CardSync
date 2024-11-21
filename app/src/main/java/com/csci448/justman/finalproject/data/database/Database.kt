package com.csci448.justman.finalproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card

@Database(entities = [Card::class, Brand::class], version = 1)
@TypeConverters(ProjectTypeConverter::class)
abstract class ProjectDatabase: RoomDatabase() {

    abstract val dao: ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDatabase(context: Context): ProjectDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "project_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}