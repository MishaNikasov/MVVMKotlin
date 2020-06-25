package com.my.project.firstkotlin.ui.fragment.favorites

import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.project.firstkotlin.data.local.repository.LocalRecipeRepo
import com.my.project.firstkotlin.data.local.room.model.RecipeModel

class SavedRecipeViewModel @ViewModelInject constructor(
    private val localRecipeRepo: LocalRecipeRepo
) : ViewModel(), Observable {

    fun getAllSavedRecipes () : LiveData<List<RecipeModel>> = localRecipeRepo.getAllRecipes()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}