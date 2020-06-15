package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val application : Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainRecipesViewModel::class.java)){
            return MainRecipesViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(NewRecipeViewModel::class.java)){
            return NewRecipeViewModel(application) as T
        }

        if (modelClass.isAssignableFrom(SearchRecipeViewModel::class.java)){
            return SearchRecipeViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}
