package com.my.project.firstkotlin.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.my.project.firstkotlin.data.room.model.Recipe

@Dao
interface RecipeDAO {

    @Insert
    suspend fun insert (recipe : Recipe)
    @Delete
    suspend fun delete (recipe: Recipe)
    @Update
    suspend fun update (recipe: Recipe)

    @Query("DELETE FROM RECIPES_TABLE")
    suspend fun deleteAllRecipes()
    @Query("SELECT * FROM RECIPES_TABLE")
    fun getAllRecipes() : LiveData<List<Recipe>>

}