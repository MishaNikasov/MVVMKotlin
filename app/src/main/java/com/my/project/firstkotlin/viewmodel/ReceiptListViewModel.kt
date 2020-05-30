package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.project.firstkotlin.data.repository.ReceiptRepo
import com.my.project.firstkotlin.data.room.model.Receipt

class ReceiptListViewModel (application : Application) : ViewModel(), Observable {

    private val receiptRepo : ReceiptRepo = ReceiptRepo(application)

    fun getAllReceipts () : LiveData<List<Receipt>>? = receiptRepo.getAllReceipts()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}