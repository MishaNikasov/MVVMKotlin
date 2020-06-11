package com.my.project.firstkotlin.data.remote.data.repository

import com.my.project.firstkotlin.data.remote.data.RecipeApiService
import com.my.project.firstkotlin.data.remote.data.response.SearchRecipeResult
import retrofit2.Response

object SearchRecipeRepository {

    suspend fun getRecipeList(recipes : String) : Response<SearchRecipeResult> {
        return RecipeApiService().searchRecipes(recipes)
    }

}