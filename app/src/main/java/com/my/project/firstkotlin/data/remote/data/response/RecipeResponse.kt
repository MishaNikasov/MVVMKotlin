package com.my.project.firstkotlin.data.remote.data.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    val baseUri: String,
    val expires: Long,
    val number: Int,
    val offset: Int,
    val processingTimeMs: Int,
    @SerializedName("results")
    val recipes: MutableList<Recipe>,
    val totalResults: Int
)