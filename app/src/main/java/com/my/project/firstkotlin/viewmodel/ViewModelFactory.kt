package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ReceiptListViewModel::class.java)){
            return ReceiptListViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(NewReceiptViewModel::class.java)){
            return NewReceiptViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}
