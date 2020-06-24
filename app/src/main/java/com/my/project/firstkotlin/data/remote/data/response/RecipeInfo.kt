package com.my.project.firstkotlin.data.remote.data.response

data class RecipeInfo(
    val analyzedInstructions: List<Instruction>,
    val creditsText: String,
    val dairyFree: Boolean,
    val dishTypes: List<String>,
    val extendedIngredients: List<Ingredient>,
    val id: Int,
    val image: String,
    val instructions: String,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val cheap: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int
)