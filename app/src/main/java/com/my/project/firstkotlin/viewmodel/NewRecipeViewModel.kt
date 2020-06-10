package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.repository.RecipeRepo
import com.my.project.firstkotlin.data.room.model.Recipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewRecipeViewModel (application : Application) : ViewModel(), Observable {

    private val recipeRepo : RecipeRepo = RecipeRepo(application)

    fun saveRecipe() {
        var recipe = Recipe(null, "fa", "23", 32, 412, .41)
        insert(recipe)
    }

    private fun insert (recipe: Recipe) : Job = viewModelScope.launch {
        recipeRepo.insert(recipe)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}