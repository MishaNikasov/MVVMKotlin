package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.project.firstkotlin.data.repository.RecipeRepo
import com.my.project.firstkotlin.data.room.model.Recipe

class RecipeListViewModel (application : Application) : ViewModel(), Observable {

    private val recipeRepo : RecipeRepo = RecipeRepo(application)

    fun getAllRecipes () : LiveData<List<Recipe>>? = recipeRepo.getAllRecipes()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}