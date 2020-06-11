package com.my.project.firstkotlin.viewmodel

import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.remote.data.repository.SearchRecipeRepository
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.remote.data.response.RecipeResult
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.data.remote.data.response.SearchRecipeResult
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.CacheResponse

class RecipeListViewModel (application : Application) : ViewModel(), Observable {

    //remote
    val searchRecipesList : MutableLiveData<Resource<List<RecipeResult>>> = MutableLiveData()
    var recipesPage = 1

    fun getAllSearchRecipes (recipes : String) = viewModelScope.launch {
        searchRecipesList.postValue(Resource.Loading())
        val response = SearchRecipeRepository.getRecipeList(recipes)
        searchRecipesList.postValue(handleSearchRecipeResponse(response))
    }

    private fun handleSearchRecipeResponse(response: Response<SearchRecipeResult>) : Resource<List<RecipeResult>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it.recipeResults)
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