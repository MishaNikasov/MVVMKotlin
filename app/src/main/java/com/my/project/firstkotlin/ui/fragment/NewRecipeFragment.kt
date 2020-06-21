package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.FragmentNewRecipeBinding
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.NewRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRecipeFragment : BaseFragment(R.layout.fragment_new_recipe) {

    private lateinit var binding: FragmentNewRecipeBinding
    private lateinit var newRecipeViewModel: NewRecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewRecipeBinding.bind(view)

        binding.newRecipeViewModel = newRecipeViewModel
        binding.lifecycleOwner = this
    }
}