package com.my.project.firstkotlin.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.my.project.firstkotlin.data.room.RecipesDatabase
import com.my.project.firstkotlin.data.room.model.Recipe
import com.my.project.firstkotlin.data.room.dao.RecipeDAO

class RecipeRepo (application: Application) {

    private val recipesDatabase : RecipesDatabase? = RecipesDatabase.getInstance(application)
    private val receiptsDao : RecipeDAO? = recipesDatabase?.receiptsDao()

    fun getAllRecipes () : LiveData<List<Recipe>>? {
        return receiptsDao?.getAllRecipes()
    }

    suspend fun deleteAllRecipes () {
        receiptsDao?.deleteAllRecipes()
    }

    suspend fun insert(recipe: Recipe){
        receiptsDao?.insert(recipe)
    }

    suspend fun update(recipe: Recipe){
        receiptsDao?.update(recipe)
    }

    suspend fun delete(recipe: Recipe){
        receiptsDao?.delete(recipe)
    }

}
