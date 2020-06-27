package com.my.project.firstkotlin.data.database.repository

import androidx.lifecycle.LiveData
import com.my.project.firstkotlin.data.database.room.model.RecipeModel
import com.my.project.firstkotlin.data.database.room.dao.RecipeDAO
import javax.inject.Inject

class DatabaseRecipeRepo @Inject constructor (
    private val receiptsDao : RecipeDAO
) {

    fun getAllRecipes () : LiveData<List<RecipeModel>> {
        return receiptsDao.getAllRecipes()
    }

    suspend fun getById(id : Int): RecipeModel? {
        return receiptsDao.getById(id)
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

    suspend fun deleteById(id: Int){
        receiptsDao.deleteById(id)
    }

}
