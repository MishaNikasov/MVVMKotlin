package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewRecipeViewModel (application : Application) : ViewModel(), Observable {

//    private val recipeRepo : RecipeRepo = RecipeRepo(application)

//    private fun insert (recipeModel: RecipeModel) : Job = viewModelScope.launch {
//        recipeRepo.insert(recipeModel)
//    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}