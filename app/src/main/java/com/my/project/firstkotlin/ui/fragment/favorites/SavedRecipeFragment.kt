package com.my.project.firstkotlin.ui.fragment.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.my.project.firstkotlin.data.TypeConverter
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.databinding.FragmentSavedRecipeBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SavedRecipeFragment : Fragment(R.layout.fragment_saved_recipe),
    RecipesAdapter.RecipeNavigator {

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

        val recipeAdapter = RecipesAdapter(Constant.ORIENTATION_VERTICAL, this)
        val recipesList : ArrayList<Recipe> = arrayListOf()

        savedRecipeViewModel.getAllSavedRecipes().observe(viewLifecycleOwner, Observer {recipesModelList ->
            for (recipeModel in recipesModelList) {
                recipesList.add(TypeConverter.localToRemoteRecipe(recipeModel))
                Timber.d(savedRecipeViewModel.getAllSavedRecipes().value?.size.toString())
                recipeAdapter.submitRecipesList(recipesList)
            }
        })

        binding.savedRecipesRecycler.adapter = recipeAdapter
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action =
            SavedRecipeFragmentDirections.actionSavedRecipesFragmentToRecipeInfoFragment(
                recipe.id
            )
        findNavController().navigate(action)
    }

}