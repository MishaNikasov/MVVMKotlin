package com.my.project.firstkotlin.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentSearchRecipeBinding
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import com.my.project.firstkotlin.ui.util.RecipeNavigator
import com.my.project.firstkotlin.viewmodel.SearchRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRecipeFragment : BaseFragment(R.layout.fragment_search_recipe), RecipeNavigator{

    private lateinit var binding: FragmentSearchRecipeBinding
    private val searchRecipeViewModel: SearchRecipeViewModel by viewModels()

    private lateinit var recipeAdapter : RecipesAdapter

    private var searchJob : Job? = null
    private var isRequestFocus = true

    private val args : SearchRecipeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchRecipeBinding.bind(view)

        binding.searchRecipeViewModel = searchRecipeViewModel
        binding.lifecycleOwner = this

        initUi()
    }

    private fun initUi () {

        args.type?.let {
            searchRecipeViewModel.type = args.type!!
            search()
            isRequestFocus = false
        }

        requestSearch()
        setUpSearchList()

        binding.searchText.doAfterTextChanged {
            it?.let {
                search(it.toString())
            }
        }

        binding.close.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        isRequestFocus = false
    }

    private fun search(txt: String = "") {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(Constant.SEARCH_DELAY)
            if (txt.isNotEmpty()) {
                searchRecipeViewModel.cleanCurrentList()
                searchRecipeViewModel.searchRecipes(txt)
            } else if (args.type != null) {
                searchRecipeViewModel.searchRecipes()
            }
        }
    }

    private fun requestSearch() {
        if (isRequestFocus) {
            binding.searchText.isFocusableInTouchMode = true;
            binding.searchText.requestFocus()

            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(binding.searchText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    //todo: add categories
    //todo: typeface
    //todo: filter
    private fun setUpSearchList() {

        val loadMoreListener : LoadMoreScrollListener

        val loadListener = object : LoadMoreScrollListener.LoadListener {
            override fun onLoad() {
                searchRecipeViewModel.searchRecipes(binding.searchText.text.toString())
            }
        }


        val linearLayoutManager = LinearLayoutManager(context)
        loadMoreListener = LoadMoreScrollListener(linearLayoutManager, loadListener)

        recipeAdapter = RecipesAdapter(Constant.ORIENTATION_VERTICAL, this)

        with(binding.recipesRecycler) {
            adapter = recipeAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(loadMoreListener)
        }

        searchRecipeViewModel.searchRecipesList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {response ->
                        stopLoading()
                        recipeAdapter.submitRecipesList(response.recipes.toList())
                        loadMoreListener.setLoaded()
                    }
                }

                is Resource.Error -> {
                    stopLoading()
                    loadMoreListener.setLoaded()
                }

                is Resource.Loading -> {
                    if (it.loading) {
                        startLoading()
                    }
                }
            }
        })
    }

    private fun stopLoading() {
        binding.loadingScreen.visibility = View.GONE
    }

    private fun startLoading() {
        binding.loadingScreen.visibility = View.VISIBLE
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action = SearchRecipeFragmentDirections.actionSearchRecipeFragmentToRecipeInfoFragment(recipe.id)
        Navigation.findNavController(binding.root).navigate(action)
    }
}
