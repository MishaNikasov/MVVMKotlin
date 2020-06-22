package com.my.project.firstkotlin.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.my.project.firstkotlin.data.local.room.model.RecipeModel

@Dao
interface RecipeDAO {

    @Insert
    suspend fun insert (recipeModel : RecipeModel)
    @Delete
    suspend fun delete (recipeModel: RecipeModel)
    @Update
    suspend fun update (recipeModel: RecipeModel)

    @Query("DELETE FROM RECIPES_TABLE WHERE remoteId=:id")
    suspend fun deleteById (id : Int)
    @Query("SELECT * FROM RECIPES_TABLE WHERE remoteId=:id")
    suspend fun getById(id : Int): RecipeModel?
    @Query("SELECT * FROM RECIPES_TABLE")
    fun getAllRecipes() : LiveData<List<RecipeModel>>

    @Query("DELETE FROM RECIPES_TABLE")
    suspend fun deleteAllRecipes()

}