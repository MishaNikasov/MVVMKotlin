package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.remote.data.repository.RecipeRepository
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchRecipeViewModel @ViewModelInject constructor (
    private val recipeRepo : RecipeRepo
) : ViewModel(), Observable {

    //remote
    val searchRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    private var searchRecipesResponse : RecipeResponse? = null
    private var lastSearch : String? = null

    fun searchRecipes (searchRecipes : String) = viewModelScope.launch {

        searchRecipesList.postValue(Resource.Loading())

        when {
            lastSearch == null -> lastSearch = searchRecipes
            lastSearch != (searchRecipes) -> {
                searchRecipesResponse = null
            }
        }

        val response =

        if (searchRecipesResponse != null)
            RecipeRepository.getRecipeResult(searchRecipes, searchRecipesResponse?.recipes!!.size)
        else
            RecipeRepository.getRecipeResult(searchRecipes)

        searchRecipesList.postValue(handleSearchRecipeResponse(response))
    }

    private fun handleSearchRecipeResponse(response: Response<RecipeResponse>) : Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (searchRecipesResponse == null) {
                    searchRecipesResponse = it
                } else {
                    val oldRecipes = searchRecipesResponse?.recipes
                    val newRecipes = it.recipes
                    oldRecipes?.addAll(newRecipes)
                }
                return Resource.Success(searchRecipesResponse ?: it)
            }
        }
        return Resource.Error("Something wrong with search: " + response.message())
    }


    //local

    fun getAllRecipes () : LiveData<List<RecipeModel>>? = recipeRepo.getAllRecipes()

    //callbacks
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}