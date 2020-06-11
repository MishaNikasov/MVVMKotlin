package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeListBinding
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

        val factory = ViewModelFactory(activity!!.application)

        recipeListViewModel = ViewModelProvider(this, factory).get(RecipeListViewModel::class.java)

        binding.recipeViewModel = recipeListViewModel
        binding.lifecycleOwner = this

        initUi()
    }

    private fun initUi() {

        setUpList()

        binding.searchBtn.setOnClickListener {
            recipeListViewModel.getAllSearchRecipes(binding.searchText.text.toString())
        }

    }

    private fun setUpList() {

        val adapter = RecipesAdapter()
        val layoutManager = LinearLayoutManager(context)

        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.recipesRecycler.adapter = adapter
        binding.recipesRecycler.layoutManager = layoutManager
        binding.recipesRecycler.setHasFixedSize(true)

        recipeListViewModel.searchRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    progressBarVisibility(false)
                    it.data?.let {responseList ->
                        adapter.setRecipesList(responseList)
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

    private fun progressBarVisibility(isShow : Boolean) {
        if (isShow)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

}

