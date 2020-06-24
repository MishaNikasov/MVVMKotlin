package com.my.project.firstkotlin.data

import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo

object TypeConverter {

    fun localToRemoteRecipe (recipe: RecipeModel) : Recipe{
        return Recipe(
            recipe.remoteId,
            recipe.mainImage,
            recipe.timeToReady,
            recipe.servings,
            recipe.sourceUrl,
            recipe.title,
            recipe.summary
        )
    }

    fun remoteToLocalRecipe (recipe: Recipe) : RecipeModel{
        return RecipeModel(
            recipe.id,
            recipe.title,
            recipe.servings,
            recipe.image,
            recipe.readyInMinutes,
            recipe.sourceUrl,
            recipe.summary
        )
    }

    fun remoteInfoToLocalRecipe (recipe: RecipeInfo) : RecipeModel{
        return RecipeModel(
            recipe.id,
            recipe.title,
            recipe.servings,
            recipe.image,
            recipe.readyInMinutes,
            recipe.sourceUrl,
            recipe.summary
        )
    }

}