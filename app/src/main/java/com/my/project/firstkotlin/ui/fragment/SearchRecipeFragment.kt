package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentSearchRecipeBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import com.my.project.firstkotlin.ui.util.RecipeNavigator
import com.my.project.firstkotlin.viewmodel.SearchRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecipeFragment : BaseFragment(R.layout.fragment_search_recipe), RecipeNavigator{

    private lateinit var binding: FragmentSearchRecipeBinding
    private val searchRecipeViewModel: SearchRecipeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchRecipeBinding.bind(view)

        binding.searchRecipeViewModel = searchRecipeViewModel
        binding.lifecycleOwner = this

        initUi()
    }


    private fun initUi () {

        setUpSearchList()

        binding.searchText.doAfterTextChanged {
            it?.let {
                if (it.toString().isNotEmpty()) {
                    searchRecipeViewModel.searchRecipes(it.toString())
                }
            }
        }
    }

    //todo: request focus on search
    //todo: add categories
    //todo: cancel search
    //todo: typefont
    //todo: hied ingredients
    private fun setUpSearchList() {

        val loadMoreListener : LoadMoreScrollListener

        val loadListener = object : LoadMoreScrollListener.LoadListener {
            override fun onLoad() {
                searchRecipeViewModel.searchRecipes(binding.searchText.text.toString())
            }
        }

        val layoutManager = LinearLayoutManager(context)
        binding.recipesRecycler.layoutManager = layoutManager
        loadMoreListener = LoadMoreScrollListener(layoutManager, loadListener)
        binding.recipesRecycler.addOnScrollListener(loadMoreListener)

        val adapter = RecipesAdapter(Constant.ORIENTATION_VERTICAL, this)
        binding.recipesRecycler.adapter = adapter

        searchRecipeViewModel.searchRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {response ->
                        adapter.setRecipesList(response.recipes.toList())
                        loadMoreListener.setLoaded()
                        adapter.stopLoading()
                    }
                }

                is Resource.Error -> {
                    loadMoreListener.setLoaded()
                    adapter.startLoading()
                }

                is Resource.Loading -> {
                    adapter.startLoading()
                }
            }
        })
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action = SearchRecipeFragmentDirections.actionSearchRecipeFragmentToRecipeInfoFragment(recipe.id)
        Navigation.findNavController(binding.root).navigate(action)
    }
}
