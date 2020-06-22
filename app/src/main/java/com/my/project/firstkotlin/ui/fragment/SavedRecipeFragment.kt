package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.FragmentSavedRecipeBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.viewmodel.SavedRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SavedRecipeFragment : Fragment(R.layout.fragment_saved_recipe) {

    private lateinit var binding: FragmentSavedRecipeBinding
    private val savedRecipeViewModel: SavedRecipeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedRecipeBinding.bind(view)

        binding.savedRecipeViewModel = savedRecipeViewModel
        binding.lifecycleOwner = this

        setUpList()
    }

    private fun setUpList() {

        val recipeAdapter = RecipesAdapter(Constant.ORIENTATION_HORIZONTAL)

        binding.savedRecipesRecycler.adapter = recipeAdapter

        Timber.d(savedRecipeViewModel.getAllSavedRecipes().value?.size.toString())

    }
}