package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.room.model.Recipe
import com.my.project.firstkotlin.databinding.FragmentRecipeListBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.RecipeListViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class RecipeListFragment : BaseFragment() {

    private lateinit var binding : FragmentRecipeListBinding
    private lateinit var recipeListViewModel : RecipeListViewModel

    override fun layoutId() = R.layout.fragment_recipe_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeListBinding.bind(view)

        val factory = ViewModelFactory(activity!!.application)

        recipeListViewModel = ViewModelProvider(this, factory).get(RecipeListViewModel::class.java)

        binding.recipeViewModel = recipeListViewModel
        binding.lifecycleOwner = this

        setUpList()
    }

    private fun setUpList() {

        val adapter = RecipesAdapter { selectedRecipe : Recipe -> onItemClick(selectedRecipe)}
        val layoutManager = LinearLayoutManager(context)

        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.recipesRecycler.adapter = adapter
        binding.recipesRecycler.layoutManager = layoutManager
        binding.recipesRecycler.setHasFixedSize(true)

        recipeListViewModel.getAllRecipes()?.observe(this, Observer {
            adapter.setRecipesList(it)
        })

    }

    private fun onItemClick(recipe : Recipe) {

    }

}

