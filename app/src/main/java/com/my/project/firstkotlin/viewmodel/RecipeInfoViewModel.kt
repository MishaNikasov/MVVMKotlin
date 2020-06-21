package com.my.project.firstkotlin.viewmodel

import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.local.repository.LocalRecipeRepo
import com.my.project.firstkotlin.data.remote.data.repository.RemoteRecipeRepository
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import com.my.project.firstkotlin.data.remote.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeInfoViewModel @ViewModelInject constructor (
    private val localRecipeRepo : LocalRecipeRepo,
    private val remoteRecipeRepository : RemoteRecipeRepository
) : ViewModel(), Observable {

    val recipeInfo : MutableLiveData<Resource<RecipeInfo>> = MutableLiveData()

    //remote
    fun getRecipeInfo (id : Int) {
        viewModelScope.launch {
            recipeInfo.postValue(Resource.Loading())
            val response = remoteRecipeRepository.getRecipeInfo(id)
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
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}