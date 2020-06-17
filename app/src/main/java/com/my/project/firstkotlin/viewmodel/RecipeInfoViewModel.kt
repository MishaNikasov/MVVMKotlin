package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.remote.data.repository.RecipeRepository
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import com.my.project.firstkotlin.data.remote.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeInfoViewModel (application : Application) : ViewModel(), Observable {

    val recipeInfo : MutableLiveData<Resource<RecipeInfo>> = MutableLiveData()

    //remote
    fun getRecipeInfo (id : Int) {
        viewModelScope.launch {
            recipeInfo.postValue(Resource.Loading())
            val response = RecipeRepository.getRecipeInfo(id)
            recipeInfo.postValue(handlePopularRecipeResponse(response))
        }
    }

    private fun handlePopularRecipeResponse (response: Response<RecipeInfo>) : Resource<RecipeInfo> {
        if (response.isSuccessful) {
            response.body()?.let { info ->
                return Resource.Success(info)
            }
        }
        return Resource.Error("Recipe info error : $response.message()")
    }

    //local
    private val recipeRepo : RecipeRepo = RecipeRepo(application)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}