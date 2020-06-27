package com.my.project.firstkotlin.ui.fragment.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.project.firstkotlin.data.local.TypeModel
import com.my.project.firstkotlin.data.local.repository.FilterRepo
import com.my.project.firstkotlin.data.remote.data.repository.RemoteRecipeRepository
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.data.remote.data.response.RecipeResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.StringBuilder

class SearchRecipeViewModel @ViewModelInject constructor (
    private val filterRepo: FilterRepo,
    private val remoteRecipeRepository : RemoteRecipeRepository
) : ViewModel() {

    //remote
    val searchRecipesList : MutableLiveData<Resource<RecipeResponse>> = MutableLiveData()
    private var searchRecipesResponse : RecipeResponse? = null

    //top filter
    val topFilterList : MutableLiveData<List<TypeModel>> = MutableLiveData()

    private lateinit var currentSearchTxt : String

    fun searchRecipes (searchRecipes : String) = viewModelScope.launch {

        currentSearchTxt = searchRecipes

        if (searchRecipesResponse != null)
            searchRecipesList.postValue(Resource.Loading(false))
        else
            searchRecipesList.postValue(Resource.Loading(true))

        val response =
            if (searchRecipesResponse != null)
                remoteRecipeRepository.getRecipeResult(currentSearchTxt, searchRecipesResponse?.recipes!!.size, convertListToString())
            else
                remoteRecipeRepository.getRecipeResult(currentSearchTxt, 0, convertListToString())

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

    fun cleanCurrentSearchList() {
        searchRecipesResponse = null
    }

    fun getTypeList() : List<TypeModel> {
        return filterRepo.getTypeList()
    }

    fun addType(item: TypeModel) {
        filterRepo.addToFilterList(item)
        updateList()
    }

    fun delType(item : TypeModel) {
        filterRepo.delFromType(item)
        updateList()
    }

    private fun updateList(){
        topFilterList.postValue(filterRepo.getFilterList())
    }

    private fun convertListToString() : String{
        var typeArray = ""
        topFilterList.value?.let {
            for (item in topFilterList.value!!) {
                typeArray = StringBuilder().append(
                    typeArray,
                    ",",
                    item.value
                ).toString()
            }
        }
        return typeArray
    }
}