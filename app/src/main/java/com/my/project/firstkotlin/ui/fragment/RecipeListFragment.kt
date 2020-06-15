package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeListBinding
import com.my.project.firstkotlin.ui.adapter.HotRecipesAdapter
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.RecipeListViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list) {

    private lateinit var binding : FragmentRecipeListBinding
    private lateinit var recipeListViewModel : RecipeListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)

        val factory = ViewModelFactory(requireActivity().application)

        recipeListViewModel = ViewModelProvider(this, factory).get(RecipeListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeListViewModel

        initUi()
    }

    private fun initUi() {

//        setUpSearchList()
        setUpPopularList()

        binding.searchText.doAfterTextChanged {
            recipeListViewModel.searchRecipes(binding.searchText.text.toString())
        }

    }

    private fun setUpPopularList() {

        recipeListViewModel.getPopularRecipes()

        val adapter = HotRecipesAdapter()

        binding.recipesRecycler.adapter = adapter
        (binding.recipesRecycler.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
        binding.recipesRecycler.addOnScrollListener(this@RecipeListFragment.scrollListener)

        recipeListViewModel.popularRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    progressBarVisibility(false)
                    it.data?.let {response ->
                        adapter.setHotRecipesList(response.recipes.toList())
                    }
                }

                is Resource.Error -> {
                    progressBarVisibility(false)
                }

                is Resource.Loading -> {
                    progressBarVisibility(true)
                }
            }
        })

    }

    private fun setUpSearchList() {

        val adapter = RecipesAdapter()

        binding.recipesRecycler.adapter = adapter
        binding.recipesRecycler.addOnScrollListener(this@RecipeListFragment.scrollListener)

        recipeListViewModel.recipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    progressBarVisibility(false)
                    it.data?.let {response ->
                        adapter.setRecipesList(response.recipes.toList())
                    }
                }

                is Resource.Error -> {
                    progressBarVisibility(false)
                }

                is Resource.Loading -> {
                    progressBarVisibility(true)
                }
            }
        })

    }

    var isLoading = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.recipesRecycler.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isLastPosition = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0

            val shouldPaginate = isLastPosition && isNotAtBeginning && !isLoading && isScrolling

            if (shouldPaginate){
                recipeListViewModel.searchRecipes(binding.searchText.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }
    }

    private fun progressBarVisibility(isShow : Boolean) {
        if (isShow) {
            isLoading = true
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            isLoading = false
            binding.progressBar.visibility = View.GONE
        }
    }

}

