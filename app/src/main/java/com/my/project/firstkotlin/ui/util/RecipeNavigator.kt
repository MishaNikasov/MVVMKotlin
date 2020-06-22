package com.my.project.firstkotlin.ui.util

import com.my.project.firstkotlin.data.remote.data.response.Recipe

interface RecipeNavigator {
    fun onRecipeClick (recipe : Recipe)
}