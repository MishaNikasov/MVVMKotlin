package com.my.project.firstkotlin.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.local.room.dao.RecipeDAO

@Database(entities = [RecipeModel::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun getReceiptsDao() : RecipeDAO

}