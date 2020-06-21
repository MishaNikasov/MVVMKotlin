package com.my.project.firstkotlin.viewmodel

import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.TypeConverter
import com.my.project.firstkotlin.data.remote.data.repository.RecipeRepository
import com.my.project.firstkotlin.data.local.repository.RecipeRepo
import com.my.project.firstkotlin.data.local.room.model.RecipeModel
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response

class MainRecipesViewModel @ViewModelInject constructor (
    private val recipeRepo : RecipeRepo
) : ViewModel(), Observable {

    //remote
    //popular
    val popularRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    private var popularRecipesResponse : RecipeResponse? = null

    //popular
    fun getPopularRecipes() = viewModelScope.launch {

        popularRecipesList.postValue(Resource.Loading())

        val response =
            if (popularRecipesResponse != null)
                RecipeRepository.getPopularRecipes(popularRecipesResponse?.recipes!!.size)
            else
                RecipeRepository.getPopularRecipes()

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
        return Resource.Error("Something wrong with popular : $response.message()")
    }

    //local

    fun addRecipeToDB(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepo.insert(TypeConverter.remoteToLocalRecipe(recipe))
        }
    }

    fun getAllRecipes () : LiveData<List<RecipeModel>>? = recipeRepo.getAllRecipes()

    //callbacks
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}