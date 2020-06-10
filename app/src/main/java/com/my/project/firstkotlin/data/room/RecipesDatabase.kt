package com.my.project.firstkotlin.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.my.project.firstkotlin.data.room.model.Recipe
import com.my.project.firstkotlin.data.room.dao.RecipeDAO

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun receiptsDao() : RecipeDAO

    companion object {

        @Volatile
        private var INSTANCE : RecipesDatabase? = null

        fun getInstance (context : Context) : RecipesDatabase? {
            if (INSTANCE == null) {
                synchronized (RecipesDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext
                        , RecipesDatabase::class.java, "receiptsDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}