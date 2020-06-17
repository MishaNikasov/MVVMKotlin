package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.RecipeInfo
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentRecipeInfoBinding
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.RecipeInfoViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class RecipeInfoFragment : BaseFragment(R.layout.fragment_recipe_info) {

    private lateinit var binding : FragmentRecipeInfoBinding
    private lateinit var recipeInfoViewModel: RecipeInfoViewModel

    private val args : RecipeInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRecipeInfoBinding.bind(view)

        val factory = ViewModelFactory(requireActivity().application)

        recipeInfoViewModel = ViewModelProvider(this, factory).get(RecipeInfoViewModel::class.java)

        binding.lifecycleOwner = this
        binding.recipeInfoViewModel = recipeInfoViewModel

        recipeInfoViewModel.getRecipeInfo(args.recipeId)

        recipeInfoViewModel.recipeInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    it.data?.let {response ->
                        initUi(response)
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

    private fun initUi(recipeInfo: RecipeInfo) {

        Glide
            .with(requireContext())
            .load(recipeInfo.image)
            .centerCrop()
            .dontAnimate()
            .placeholder(R.drawable.recipe_holder)
            .into(binding.image)

        binding.title.text = recipeInfo.title
        binding.description.text = HtmlCompat.fromHtml(recipeInfo.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        Linkify.addLinks(binding.description, Linkify.WEB_URLS);
    }

}
