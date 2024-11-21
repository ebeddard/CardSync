package com.csci448.justman.finalproject.data.database

import androidx.room.*
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State
import java.util.*

@Dao
interface ProjectDao {

    @Insert(entity = Card::class)
    suspend fun addCard(card: Card)

    @Insert(entity = Brand::class)
    suspend fun addBrand(brand: Brand)

    @Query("SELECT * FROM brands")
    fun getBrands(): Flow<List<Brand>>

    @Query("SELECT * FROM cards")
    fun getCards(): Flow<List<Card>>

    @Query("SELECT * FROM cards WHERE id = :cardID")
    suspend fun getCardByID(cardID: UUID): Card?

    @Query("SELECT * FROM cards WHERE brandID = :uuid")
    fun getCardByBrandID(uuid: UUID): Flow<List<Card>>

    @Query("SELECT * FROM brands WHERE id = :uuid")
    suspend fun getBrandByID(uuid: UUID): Brand?

    @Delete(entity = Card::class)
    suspend fun deleteCard(card: Card)

    @Delete(entity = Brand::class)
    suspend fun deleteBrand(brand: Brand)

    @Update(entity = Card::class)
    suspend fun updateBalanceByCard(card: Card)

}