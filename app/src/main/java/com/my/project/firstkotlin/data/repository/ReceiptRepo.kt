package com.my.project.firstkotlin.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.my.project.firstkotlin.data.room.ReceiptsDatabase
import com.my.project.firstkotlin.data.room.model.Receipt
import com.my.project.firstkotlin.data.room.model.ReceiptDAO

class ReceiptRepo (application: Application) {

    private val receiptsDatabase : ReceiptsDatabase? = ReceiptsDatabase.getInstance(application)
    private val receiptsDao : ReceiptDAO? = receiptsDatabase?.receiptsDao()

    fun getAllReceipts () : LiveData<List<Receipt>>? {
        return receiptsDao?.getAllReceipts()
    }

    suspend fun insert(receipt: Receipt){
        receiptsDao?.insert(receipt)
    }

    suspend fun update(receipt: Receipt){
        receiptsDao?.update(receipt)
    }

    suspend fun delete(receipt: Receipt){
        receiptsDao?.delete(receipt)
    }

}
