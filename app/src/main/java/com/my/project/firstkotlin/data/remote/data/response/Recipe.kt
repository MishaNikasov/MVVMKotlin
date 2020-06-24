package com.my.project.firstkotlin.data.remote.data.response

data class Recipe(
    val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String,
    val summary: String? = null
)