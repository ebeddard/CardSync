package com.csci448.justman.finalproject.data

import android.content.Context
import android.util.Log
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import com.csci448.justman.finalproject.data.database.ProjectDao
import com.csci448.justman.finalproject.data.database.ProjectDatabase
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class ProjectRepo private constructor(private val dao: ProjectDao,
                                         private val coroutineScope: CoroutineScope = GlobalScope
) {
    companion object {
        private const val LOG_TAG = "448.ProjectRepo"
        private var INSTANCE: ProjectRepo? = null

        /**
         * @param context
         */
        fun getInstance(context: Context): ProjectRepo {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "project_database"
                ).build()
                val dao = database.dao
                val repo = ProjectRepo(dao)
                INSTANCE = repo
                repo
            }
        }
    }

    suspend fun getCard(id: UUID): Card? {
        return dao.getCardByID(id)
    }

    fun addCard(card: Card) {
        coroutineScope.launch {
            dao.addCard(card)
        }
    }

    fun getBrands(): Flow<List<Brand>> {
        return dao.getBrands()
    }

    fun getCards(): Flow<List<Card>> {
        return dao.getCards()
    }

    suspend fun getCardByID(cardID: UUID): Card? {
        return dao.getCardByID(cardID)
    }
    fun getCardsByBrandID(id: UUID): Flow<List<Card>> {
        return dao.getCardByBrandID(id)
    }

    fun addBrand(brand: Brand) {
        coroutineScope.launch {
            dao.addBrand(brand)
        }
    }

    suspend fun getBrandByID(uuid: UUID): Brand? {
        return dao.getBrandByID(uuid)
    }

    suspend fun updateBalanceByCard(card: Card) {
        dao.updateBalanceByCard(card)
    }

    suspend fun deleteBrand(brand: Brand) {
        dao.deleteBrand(brand)
    }

    suspend fun deleteCard(card: Card) {
        dao.deleteCard(card)
    }

    init {

    }

}