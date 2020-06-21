package com.my.project.firstkotlin.data.local.repository

import androidx.lifecycle.LiveData
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.local.room.dao.RecipeDAO
import javax.inject.Inject

class RecipeRepo @Inject constructor (
    private val receiptsDao : RecipeDAO
) {

    fun getAllRecipes () : LiveData<List<RecipeModel>>? {
        return receiptsDao.getAllRecipes()
    }

    suspend fun deleteAllRecipes () {
        receiptsDao.deleteAllRecipes()
    }

    suspend fun insert(recipeModel: RecipeModel){
        receiptsDao.insert(recipeModel)
    }

    suspend fun update(recipeModel: RecipeModel){
        receiptsDao.update(recipeModel)
    }

    suspend fun delete(recipeModel: RecipeModel){
        receiptsDao.delete(recipeModel)
    }

}
