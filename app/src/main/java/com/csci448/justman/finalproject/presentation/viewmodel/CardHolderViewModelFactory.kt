package com.csci448.justman.finalproject.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csci448.justman.finalproject.data.ProjectRepo

class CardHolderViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        private const val LOG_TAG = "448.CardHolderViewModelFactory"
    }
    private val repository: ProjectRepo by lazy {
        ProjectRepo.getInstance(context)
    }
    fun getViewModelClass() = CardHolderViewModel::class.java

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(LOG_TAG, "create() called")
        if(modelClass.isAssignableFrom(getViewModelClass())) {
            Log.d(LOG_TAG, "creating ViewModel: ${getViewModelClass()}")
            return modelClass
                .getConstructor(ProjectRepo::class.java)
                .newInstance(repository)
        }
        Log.e(LOG_TAG, "Unknown ViewModel: $modelClass")
        throw IllegalArgumentException("Unknown ViewModel")
    }
}