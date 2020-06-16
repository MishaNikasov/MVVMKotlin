package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.util.CommonRemote
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentSearchRecipeBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import com.my.project.firstkotlin.viewmodel.SearchRecipeViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchRecipeFragment : BaseFragment(R.layout.fragment_search_recipe){

    private lateinit var binding: FragmentSearchRecipeBinding
    private lateinit var searchRecipeViewModel: SearchRecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchRecipeBinding.bind(view)

        val factory = ViewModelFactory(requireActivity().application)

        searchRecipeViewModel = ViewModelProvider(this, factory).get(SearchRecipeViewModel::class.java)
        binding.searchRecipeViewModel = searchRecipeViewModel
        binding.lifecycleOwner = this

        initUi()
    }


    private fun initUi () {

        setUpSearchList()

        binding.searchText.doAfterTextChanged {
            it?.let {
                if (it.toString().isNotEmpty()) {
                    searchRecipeViewModel.searchRecipes(it.toString())
                }
            }
        }
    }

    private fun setUpSearchList() {

        val loadMoreListener : LoadMoreScrollListener

        val loadListener = object : LoadMoreScrollListener.LoadListener {
            override fun onLoad() {
                searchRecipeViewModel.searchRecipes(binding.searchText.text.toString())
            }
        }

        val layoutManager = LinearLayoutManager(context)
        binding.recipesRecycler.layoutManager = layoutManager
        loadMoreListener = LoadMoreScrollListener(layoutManager, loadListener)
        binding.recipesRecycler.addOnScrollListener(loadMoreListener)

        val adapter = RecipesAdapter(Constant.ORIENTATION_VERTICAL)
        binding.recipesRecycler.adapter = adapter

        searchRecipeViewModel.searchRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {response ->
                        adapter.setRecipesList(response.recipes.toList())
                        loadMoreListener.setLoaded()
                        adapter.stopLoading()
                    }
                }

                is Resource.Error -> {
                    loadMoreListener.setLoaded()
                    adapter.startLoading()
                }

                is Resource.Loading -> {
                    adapter.startLoading()
                }
            }
        })
    }
}
