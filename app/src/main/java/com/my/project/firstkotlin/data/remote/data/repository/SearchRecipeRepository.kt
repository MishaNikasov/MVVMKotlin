package com.my.project.firstkotlin.data.remote.data.repository

import com.my.project.firstkotlin.data.remote.data.RecipeApiService
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import retrofit2.Response

object SearchRecipeRepository {

    suspend fun getRecipeResult(recipes : String, offset : Int = 0) : Response<RecipeResponse> {
        return RecipeApiService().searchRecipes(recipes, offset)
    }

    suspend fun getPopularRecipes(offset : Int = 0) : Response<RecipeResponse> {
        return RecipeApiService().popularRecipes(offset)
    }

}