package com.my.project.firstkotlin.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.local.uimodel.TypeModel
import com.my.project.firstkotlin.data.remote.data.repository.RemoteRecipeRepository
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainRecipesViewModel @ViewModelInject constructor (
    private val remoteRecipeRepository : RemoteRecipeRepository
) : ViewModel() {

    //remote
    val popularRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    private var popularRecipesResponse : RecipeResponse? = null

    //popular
    fun getPopularRecipes() = viewModelScope.launch {

        popularRecipesList.postValue(Resource.Loading())

        val response =
            if (popularRecipesResponse != null)
                remoteRecipeRepository.getPopularRecipes(popularRecipesResponse?.recipes!!.size)
            else
                remoteRecipeRepository.getPopularRecipes()

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
    fun getType() : List<TypeModel> {
        return TypeModel.getTypeRecipe()
    }
}