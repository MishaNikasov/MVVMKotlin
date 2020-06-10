package com.my.project.firstkotlin.data.remote.data.response

data class RecipeResult(
    val id: Int,
    val image: String,
    val openLicense: Int,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)