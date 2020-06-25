package com.my.project.firstkotlin.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.local.uimodel.TypeModel
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentMainRecipesBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.adapter.TypeModelAdapter
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainRecipeFragment :
    Fragment(R.layout.fragment_main_recipes),
    RecipesAdapter.RecipeNavigator
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

        setTypes()

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
                        adapter.submitRecipesList(response.recipes.toList())
                        loadMoreListener.setLoaded()
                    }
                }

                is Resource.Error -> {
                    loadMoreListener.setLoaded()
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun setTypes() {

        val layoutManager = GridLayoutManager(context, 3)
        val adapter = TypeModelAdapter(recipeListViewModel.getType(), object : TypeModelAdapter.Interaction{
            override fun onItemSelected(position: Int, item: TypeModel) {
                openSearch(item.value)
            }
        })
        binding.typeRecycler.adapter = adapter
        binding.typeRecycler.layoutManager = layoutManager
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action =
            MainRecipeFragmentDirections.actionMainRecipeFragmentToRecipeInfoFragment(
                recipe.id
            )
        findNavController().navigate(action)
    }

    private fun openSearch(type : String? = null) {
        val action =
            MainRecipeFragmentDirections.actionMainRecipeFragmentToSearchRecipeFragment(
                type
            )
        findNavController().navigate(action)
    }

}

