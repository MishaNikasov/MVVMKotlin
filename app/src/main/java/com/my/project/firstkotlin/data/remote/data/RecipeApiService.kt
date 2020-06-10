package com.my.project.firstkotlin.data.remote.data

import retrofit2.http.GET

//https://api.spoonacular.com/recipes/search?query=meat&number=1&apiKey=01adad87682e41e6828052790566359b

interface RecipeApiService {

    @GET("recipes/search")
    fun searchRecipes(

    )

}