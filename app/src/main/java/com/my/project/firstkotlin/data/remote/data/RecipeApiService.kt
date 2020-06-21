package com.my.project.firstkotlin.data.remote.data

import com.my.project.firstkotlin.data.remote.util.CommonRemote
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("query") receipt : String,
        @Query("offset") offset : Int = 0,
        @Query("number") number : Int = CommonRemote.ITEMS_COUNT
    ) : Response<RecipeResponse>

    @GET("recipes/search")
    suspend fun popularRecipes(
        @Query("offset") offset : Int = 0,
        @Query("number") number : Int = CommonRemote.ITEMS_COUNT,
        @Query("query") receipt : String = "Popular"
    ) : Response<RecipeResponse>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInfo(
        @Path("id") id : Int,
        @Query("includeNutrition") includeNutrition : Boolean = false
    ) : Response<RecipeInfo>

}