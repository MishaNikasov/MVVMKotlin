package com.my.project.firstkotlin.data.remote.data.repository

import com.my.project.firstkotlin.data.remote.data.RecipeApiService
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import retrofit2.Response
import javax.inject.Inject

class RemoteRecipeRepository @Inject constructor (
    private val recipeApiService: RecipeApiService
) {

    suspend fun getRecipeResult(recipes : String = "", offset : Int = 0, type : String = "" ) : Response<RecipeResponse> {
        return recipeApiService.searchRecipes(recipes, offset, type)
    }

    suspend fun getPopularRecipes(offset : Int = 0) : Response<RecipeResponse> {
        return recipeApiService.popularRecipes(offset)
    }

    suspend fun getRecipeInfo (id : Int) : Response<RecipeInfo> {
        return recipeApiService.getRecipeInfo(id)
    }

}