package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeListBinding
import com.my.project.firstkotlin.ui.adapter.HorizontalRecipesAdapter
import com.my.project.firstkotlin.ui.adapter.VerticalRecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.MainRecipesViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class RecipeListFragment : BaseFragment(R.layout.fragment_recipe_list) {

    private lateinit var binding : FragmentRecipeListBinding
    private lateinit var recipeListViewModel : MainRecipesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)

        val factory = ViewModelFactory(requireActivity().application)

        recipeListViewModel = ViewModelProvider(this, factory).get(MainRecipesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.recipeViewModel = recipeListViewModel

        initUi()
    }

    private fun initUi() {
        setUpPopularList()
        recipeListViewModel.getPopularRecipes()
    }

    //top popular recipes
    private fun setUpPopularList() {

        val adapter = HorizontalRecipesAdapter()

        binding.popularRecipesRecycler.adapter = adapter
        (binding.popularRecipesRecycler.layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.HORIZONTAL
        binding.popularRecipesRecycler.addOnScrollListener(this@RecipeListFragment.scrollListener)

        recipeListViewModel.popularRecipesList.observe(viewLifecycleOwner, Observer {
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

            val layoutManager = binding.popularRecipesRecycler.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isLastPosition = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0

            val shouldPaginate = isLastPosition && isNotAtBeginning && !isLoading && isScrolling

            if (shouldPaginate){
                recipeListViewModel.getPopularRecipes()
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
//            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            isLoading = false
//            binding.progressBar.visibility = View.GONE
        }
    }

}

