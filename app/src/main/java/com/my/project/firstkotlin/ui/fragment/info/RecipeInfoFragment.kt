package com.my.project.firstkotlin.ui.fragment.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Ingredient
import com.my.project.firstkotlin.data.remote.data.response.Instruction
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeInfoBinding
import com.my.project.firstkotlin.ui.adapter.IngredientsAdapter
import com.my.project.firstkotlin.ui.adapter.InstructionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeInfoFragment : Fragment(R.layout.fragment_recipe_info) {

    private lateinit var binding : FragmentRecipeInfoBinding
    private val recipeInfoViewModel: RecipeInfoViewModel by viewModels()

    private val args : RecipeInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeInfoBinding.bind(view)

        binding.lifecycleOwner = this
        binding.recipeInfoViewModel = recipeInfoViewModel

        recipeInfoViewModel.getRecipeInfo(args.recipeId)
        recipeInfoViewModel.getLocalRecipe(args.recipeId)

        recipeInfoViewModel.recipeInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    it.data?.let {response ->
                        binding.recipeInfo = response
                        setUpIngredientsList(response.extendedIngredients)
                        if (response.analyzedInstructions.isNotEmpty()) {
                            setUpInstructionList(response.analyzedInstructions[0].steps)
                        }
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

        recipeInfoViewModel.localRecipe.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.favoriteIco.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_full))
            } else {
                binding.favoriteIco.setImageDrawable(resources.getDrawable(R.drawable.ic_favorites_empty))
            }
        })

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
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
