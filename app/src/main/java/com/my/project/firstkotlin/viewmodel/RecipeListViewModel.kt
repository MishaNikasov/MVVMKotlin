package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.remote.data.repository.SearchRecipeRepository
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.CommonRemote
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeListViewModel (application : Application) : ViewModel(), Observable {

    companion object {
        const val SEARCH = 0
        const val POPULAR = 1
    }

    //remote
    val recipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    val popularRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    var recipesCount = CommonRemote.ITEMS_COUNT
    var lastSearch : String? = null
    var searchRecipesResponse : RecipeResponse? = null

    //search
    fun searchRecipes (searchRecipes : String) = viewModelScope.launch {

        recipesList.postValue(Resource.Loading())

        when {
            lastSearch == null -> lastSearch = searchRecipes
            lastSearch != (searchRecipes) -> {
                searchRecipesResponse = null
            }
        }

        val response =

        if (searchRecipesResponse != null)
            SearchRecipeRepository.getRecipeResult(searchRecipes, recipesCount, searchRecipesResponse?.recipes!!.size)
        else
            SearchRecipeRepository.getRecipeResult(searchRecipes, recipesCount)

        recipesList.postValue(handleSearchRecipeResponse(response))
    }

    //popular
    fun getPopularRecipes() =  viewModelScope.launch {



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
    private val recipeRepo : RecipeRepo = RecipeRepo(application)

    fun getAllRecipes () : LiveData<List<RecipeModel>>? = recipeRepo.getAllRecipes()

    //callbacks
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}