package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.repository.ReceiptRepo
import com.my.project.firstkotlin.data.room.model.Receipt
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewReceiptViewModel (application : Application) : ViewModel(), Observable {

    private val receiptRepo : ReceiptRepo = ReceiptRepo(application)

    fun saveReceipt() {
        var receipt = Receipt(null, "fa", "23", 32, 412, .41)
        insert(receipt)
    }

    private fun insert (receipt: Receipt) : Job = viewModelScope.launch {
        receiptRepo.insert(receipt)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}