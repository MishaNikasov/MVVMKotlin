package com.my.project.firstkotlin.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.my.project.firstkotlin.data.local.room.RecipesDatabase
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.local.room.dao.RecipeDAO

class RecipeRepo (application: Application) {

    private val recipesDatabase : RecipesDatabase? = RecipesDatabase.getInstance(application)
    private val receiptsDao : RecipeDAO? = recipesDatabase?.receiptsDao()

    fun getAllRecipes () : LiveData<List<RecipeModel>>? {
        return receiptsDao?.getAllRecipes()
    }

    suspend fun deleteAllRecipes () {
        receiptsDao?.deleteAllRecipes()
    }

    suspend fun insert(recipeModel: RecipeModel){
        receiptsDao?.insert(recipeModel)
    }

    suspend fun update(recipeModel: RecipeModel){
        receiptsDao?.update(recipeModel)
    }

    suspend fun delete(recipeModel: RecipeModel){
        receiptsDao?.delete(recipeModel)
    }

}
