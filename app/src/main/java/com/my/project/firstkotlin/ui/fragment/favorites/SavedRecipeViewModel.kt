package com.my.project.firstkotlin.ui.fragment.favorites

import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.project.firstkotlin.data.database.repository.DatabaseRecipeRepo
import com.my.project.firstkotlin.data.database.room.model.RecipeModel

class SavedRecipeViewModel @ViewModelInject constructor(
    private val databaseRecipeRepo: DatabaseRecipeRepo
) : ViewModel(), Observable {

    fun getAllSavedRecipes () : LiveData<List<RecipeModel>> = databaseRecipeRepo.getAllRecipes()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}