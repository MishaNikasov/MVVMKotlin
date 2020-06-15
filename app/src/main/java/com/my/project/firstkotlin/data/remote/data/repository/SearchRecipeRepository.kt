package com.my.project.firstkotlin.data.remote.data.repository

import com.my.project.firstkotlin.data.remote.data.RecipeApiService
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import retrofit2.Response

object SearchRecipeRepository {

    suspend fun getRecipeResult(recipes : String, page : Int, offset : Int = 0) : Response<RecipeResponse> {
        return RecipeApiService().searchRecipes(recipes, page, offset)
    }

}