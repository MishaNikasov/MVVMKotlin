package com.my.project.firstkotlin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.my.project.firstkotlin.data.room.model.Receipt
import com.my.project.firstkotlin.data.room.model.ReceiptDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Receipt::class], version = 1, exportSchema = false)
abstract class ReceiptsDatabase : RoomDatabase() {

    abstract fun receiptsDao() : ReceiptDAO

    companion object {

        @Volatile
        private var INSTANCE : ReceiptsDatabase? = null

        fun getInstance (context : Context) : ReceiptsDatabase? {
            if (INSTANCE == null) {
                synchronized (ReceiptsDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext
                        , ReceiptsDatabase::class.java, "receiptsDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}