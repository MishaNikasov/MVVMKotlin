package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ReceiptViewModelFactory(private val application : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceiptViewModel::class.java)){
            return ReceiptViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
