package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentMainRecipesBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import com.my.project.firstkotlin.ui.util.RecipeNavigator
import com.my.project.firstkotlin.viewmodel.MainRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainRecipeFragment :
    BaseFragment(R.layout.fragment_main_recipes),
    RecipeNavigator
{

    private lateinit var binding : FragmentMainRecipesBinding
    private val recipeListViewModel : MainRecipesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainRecipesBinding.bind(view)

        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeListViewModel

        initUi()
    }

    private fun initUi() {
        setUpPopularList()
        recipeListViewModel.getPopularRecipes()

        binding.searchBtn.setOnClickListener {
            openSearch()
        }
    }

    //top popular recipes
    private fun setUpPopularList() {

        val loadMoreListener : LoadMoreScrollListener

        val loadListener = object : LoadMoreScrollListener.LoadListener {
            override fun onLoad() {
                recipeListViewModel.getPopularRecipes()
            }
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.popularRecipesRecycler.layoutManager = layoutManager

        loadMoreListener = LoadMoreScrollListener(layoutManager, loadListener)
        binding.popularRecipesRecycler.addOnScrollListener(loadMoreListener)

        val adapter = RecipesAdapter(Constant.ORIENTATION_HORIZONTAL, this)
        binding.popularRecipesRecycler.adapter = adapter

        recipeListViewModel.popularRecipesList.observe(viewLifecycleOwner, Observer {
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
                    adapter.stopLoading()
                }

                is Resource.Loading -> {
                    adapter.startLoading()
                }
            }
        })
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action = MainRecipeFragmentDirections.actionMainRecipeFragmentToRecipeInfoFragment(recipe.id)
        Navigation.findNavController(binding.root).navigate(action)
    }

    fun openSearch() {
        val action = MainRecipeFragmentDirections.actionMainRecipeFragmentToSearchRecipeFragment()
        Navigation.findNavController(binding.root).navigate(action)
    }

}

