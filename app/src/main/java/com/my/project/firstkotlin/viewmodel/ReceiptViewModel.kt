package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.repository.ReceiptRepo
import com.my.project.firstkotlin.data.room.model.Receipt
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ReceiptViewModel (application : Application) : ViewModel(), Observable {

    private val receiptRepo : ReceiptRepo = ReceiptRepo(application)
    private val allReceipts : LiveData<List<Receipt>>? = receiptRepo.getAllReceipts()

    @Bindable
    val title = MutableLiveData<String>()

    fun saveOrUpdate() {
        insert(Receipt(null, title.value!!, "23", 32, 412, .41))
    }

    fun getAllReceipts () : LiveData<List<Receipt>>? {
        return allReceipts
    }

    fun insert (receipt: Receipt) : Job = viewModelScope.launch {
        receiptRepo.insert(receipt)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}