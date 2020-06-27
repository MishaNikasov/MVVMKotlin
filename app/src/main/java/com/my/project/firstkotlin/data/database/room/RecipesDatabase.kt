package com.my.project.firstkotlin.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.project.firstkotlin.data.database.room.model.RecipeModel
import com.my.project.firstkotlin.data.database.room.dao.RecipeDAO
import com.my.project.firstkotlin.ui.util.Constant

@Database(entities = [RecipeModel::class], version = Constant.DATABASE_VERSION, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun getReceiptsDao() : RecipeDAO
}