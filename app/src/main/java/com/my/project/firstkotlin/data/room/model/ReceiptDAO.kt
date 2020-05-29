package com.my.project.firstkotlin.data.room.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReceiptDAO {

    @Insert
    suspend fun insert (receipt : Receipt)
    @Delete
    suspend fun delete (receipt: Receipt)
    @Update
    suspend fun update (receipt: Receipt)

    @Query("DELETE FROM RECEIPT_TABLE")
    suspend fun deleteAllReceipts()
    @Query("SELECT * FROM RECEIPT_TABLE")
    fun getAllReceipts() : LiveData<List<Receipt>>

}