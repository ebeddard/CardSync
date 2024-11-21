package com.csci448.justman.finalproject.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.csci448.justman.finalproject.data.ProjectRepo
import com.csci448.justman.finalproject.data.model.Brand
import com.csci448.justman.finalproject.data.model.Card
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class CardHolderViewModel(private val projectRepo: ProjectRepo): ViewModel() {

    companion object {
        private const val LOG_TAG = "448.CardViewModel"
    }

    // Cards

    private val mAllCards: MutableStateFlow<List<Card>> =
        MutableStateFlow(emptyList())

    private val mCards: MutableStateFlow<List<Card>> =
            MutableStateFlow(emptyList())

    private val mCurrentCardIdState: MutableStateFlow<UUID> =
            MutableStateFlow(UUID.randomUUID())

    val cardListState: StateFlow<List<Card>>
        get() = mCards.asStateFlow()

    val allCardListState: StateFlow<List<Card>>
        get() = mAllCards.asStateFlow()

    private val mCurrentCardState: MutableStateFlow<Card?> = MutableStateFlow(null)

    val currentCardState: StateFlow<Card?>
        get() = mCurrentCardState.asStateFlow()

    // Brands

    private val mBrands: MutableStateFlow<List<Brand>> =
            MutableStateFlow(emptyList())

    val brandListState: StateFlow<List<Brand>>
        get() = mBrands.asStateFlow()

    private val mCurrentBrandIdState: MutableStateFlow<UUID>
            = MutableStateFlow(UUID.randomUUID())

    private val mCurrentBrandState: MutableStateFlow<Brand?> = MutableStateFlow(null)

    val currentBrandState: StateFlow<Brand?>
        get() = mCurrentBrandState.asStateFlow()

    fun addCard(card: Card) {
        projectRepo.addCard(card)
    }

    fun addBrand(brand: Brand) {
        projectRepo.addBrand(brand)
    }


    fun updateBalance(balance: String) {
        val card = Card(
            balance,
            currentCardState.value!!.cardNumber,
            currentCardState.value!!.securityNumber,
            currentCardState.value!!.expirationDate,
            currentCardState.value!!.photoID,
            currentCardState.value!!.brandID,
            currentCardState.value!!.id
        )
        if (card != null) {
            viewModelScope.launch {
                    mCurrentCardState.update { card }
                    projectRepo.updateBalanceByCard(card)
            }
        }
    }

     fun deleteBrand(brand: Brand?) {
         viewModelScope.launch {
             if (brand != null)
                 projectRepo.deleteBrand(brand)
         }
    }

    fun deleteCard(card: Card?) {
        viewModelScope.launch {  if (card != null){
            projectRepo.deleteCard(card)
        } }

    }

        fun getCardCount(uuid: UUID): Int {
            return mAllCards.value.count { card -> card.brandID == uuid }
        }

        fun getBrandTotal(uuid: UUID): Double {
            return mAllCards.value.filter { it.brandID == uuid }
                .map { it.balance }
                .sumOf { it.toDouble() }
        }

        fun loadCardByID(uuid: UUID) {
            mCurrentCardIdState.update { uuid }
        }

        fun loadBrandByID(uuid: UUID) {
            Log.d(LOG_TAG, uuid.toString())
            mCurrentBrandIdState.update { uuid }

        }

        init {
            viewModelScope.launch {
                Log.d(LOG_TAG, mCurrentCardIdState.value.toString())
                mCurrentCardIdState
                    .map { uuid -> projectRepo.getCardByID(uuid) }
                    .collect { card -> mCurrentCardState.update { card } }
            }
            viewModelScope.launch {
                projectRepo.getBrands().collect { brands ->
                    mBrands.update { brands }
                }
            }
            viewModelScope.launch {
                projectRepo.getCards().collect { cards ->
                    mAllCards.update { cards }
                }
            }
            viewModelScope.launch {
                mCurrentBrandIdState
                    .map { uuid -> projectRepo.getBrandByID(uuid) }
                    .collect { brand ->
                        mCurrentBrandState.update { brand }
                    }

            }
            mCurrentBrandIdState
                .flatMapLatest { brandId ->
                    projectRepo.getCardsByBrandID(brandId)
                }
                .onEach { cardList ->
                    mCards.update { cardList }
                }
                .launchIn(viewModelScope)


        }

}