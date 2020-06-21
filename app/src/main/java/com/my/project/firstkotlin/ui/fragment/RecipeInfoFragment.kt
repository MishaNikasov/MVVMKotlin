package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Ingredient
import com.my.project.firstkotlin.data.remote.data.response.Instruction
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeInfoBinding
import com.my.project.firstkotlin.ui.adapter.IngredientsAdapter
import com.my.project.firstkotlin.ui.adapter.InstructionAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.RecipeInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeInfoFragment : BaseFragment(R.layout.fragment_recipe_info) {

    private lateinit var binding : FragmentRecipeInfoBinding
    private val recipeInfoViewModel: RecipeInfoViewModel by viewModels()

    private val args : RecipeInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeInfoBinding.bind(view)

        binding.lifecycleOwner = this
        binding.recipeInfoViewModel = recipeInfoViewModel

        recipeInfoViewModel.getRecipeInfo(args.recipeId)

        recipeInfoViewModel.recipeInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    it.data?.let {response ->
                        binding.recipeInfo = response
                        setUpIngredientsList(response.extendedIngredients)
                        setUpInstructionList(response.analyzedInstructions[0].steps)
                    }
                }

                is Resource.Error -> {
                    binding.loadingScreen.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.loadingScreen.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpIngredientsList(ingredients : List<Ingredient>) {
        val adapter = IngredientsAdapter()
        adapter.differ.submitList(ingredients)
        binding.ingredientsRecycler.adapter = adapter
    }

    private fun setUpInstructionList(steps : List<Instruction.Step>) {
        val adapter = InstructionAdapter(steps)
        binding.instructionRecycler.adapter = adapter
    }
}
