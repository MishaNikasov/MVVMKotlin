package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.CommonRemote
import com.my.project.firstkotlin.data.remote.Resource
import com.my.project.firstkotlin.databinding.FragmentSearchRecipeBinding
import com.my.project.firstkotlin.ui.adapter.VerticalRecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.SearchRecipeViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchRecipeFragment : BaseFragment(R.layout.fragment_search_recipe) {

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

        var job : Job? = null
        binding.searchText

        binding.searchText.doAfterTextChanged {
            job?.cancel()
            job = MainScope().launch {
                delay(CommonRemote.SEARCH_DELAY)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        searchRecipeViewModel.searchRecipes(it.toString())
                    }
                }
            }
        }
    }

    private fun setUpSearchList() {

        val adapter = VerticalRecipesAdapter()

        binding.recipesRecycler.adapter = adapter
        binding.recipesRecycler.addOnScrollListener(this@SearchRecipeFragment.scrollListener)

        searchRecipeViewModel.searchRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {response ->
                        adapter.setRecipesList(response.recipes.toList())
                    }
                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    var isLoading = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.recipesRecycler.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isLastPosition = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0

            val shouldPaginate = isLastPosition && isNotAtBeginning && !isLoading && isScrolling

            if (shouldPaginate){
                searchRecipeViewModel.searchRecipes(binding.searchText.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }
    }
}
