package com.my.project.firstkotlin.data

import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.data.response.Recipe

object TypeConverter {

    fun localToRemoteRecipe (recipe: RecipeModel) : Recipe{
        return Recipe(
            recipe.remoteId,
            recipe.mainImage,
            recipe.timeToReady,
            recipe.servings,
            recipe.sourceUrl,
            recipe.title
        )
    }

    fun remoteToLocalRecipe (recipe: Recipe) : RecipeModel{
        return RecipeModel(
            recipe.id,
            recipe.title,
            recipe.servings,
            recipe.image,
            recipe.readyInMinutes,
            recipe.sourceUrl
        )
    }

}