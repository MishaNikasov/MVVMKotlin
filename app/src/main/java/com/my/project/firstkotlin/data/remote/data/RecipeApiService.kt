package com.my.project.firstkotlin.data.remote.data

import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import com.my.project.firstkotlin.ui.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") receipt : String = "",
        @Query("offset") offset : Int = 0,
        @Query("type") type : String? = null,
        @Query("addRecipeInformation") addRecipeInformation : Boolean = true,
        @Query("number") number : Int = Constant.ITEMS_COUNT
    ) : Response<RecipeResponse>

    @GET("recipes/search")
    suspend fun popularRecipes(
        @Query("offset") offset : Int = 0,
        @Query("number") number : Int = Constant.ITEMS_COUNT,
        @Query("query") receipt : String = "Popular"
    ) : Response<RecipeResponse>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id : Int,
        @Query("includeNutrition") includeNutrition : Boolean = false
    ) : Response<RecipeInfo>

}