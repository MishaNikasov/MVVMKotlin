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
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainRecipesViewModel (application : Application) : ViewModel(), Observable {

    //remote
    //popular
    val popularRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    private var popularRecipesResponse : RecipeResponse? = null

    //popular
    fun getPopularRecipes() = viewModelScope.launch {

        popularRecipesList.postValue(Resource.Loading())

        val response =
            if (popularRecipesResponse != null)
                SearchRecipeRepository.getPopularRecipes(popularRecipesResponse?.recipes!!.size)
            else
                SearchRecipeRepository.getPopularRecipes()

        popularRecipesList.postValue(handlePopularRecipeResponse(response))
    }

    private fun handlePopularRecipeResponse(response: Response<RecipeResponse>) : Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (popularRecipesResponse == null) {
                    popularRecipesResponse = it
                } else {
                    val oldRecipes = popularRecipesResponse?.recipes
                    val newRecipes = it.recipes
                    oldRecipes?.addAll(newRecipes)
                }
                return Resource.Success(popularRecipesResponse ?: it)
            }
        }
        return Resource.Error("Something wrong with popular: " + response.message())
    }

    //local
    private val recipeRepo : RecipeRepo = RecipeRepo(application)

    fun getAllRecipes () : LiveData<List<RecipeModel>>? = recipeRepo.getAllRecipes()

    //callbacks
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}