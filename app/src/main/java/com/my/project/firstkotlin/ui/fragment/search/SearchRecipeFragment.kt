package com.my.project.firstkotlin.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.data.local.TypeModel
import com.my.project.firstkotlin.data.remote.data.response.Recipe
import com.my.project.firstkotlin.data.remote.util.Resource
import com.my.project.firstkotlin.databinding.FragmentSearchRecipeBinding
import com.my.project.firstkotlin.ui.adapter.FilterAdapter
import com.my.project.firstkotlin.ui.adapter.RecipesAdapter
import com.my.project.firstkotlin.ui.util.Constant
import com.my.project.firstkotlin.ui.util.LoadMoreScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_filter.*
import kotlinx.android.synthetic.main.bottom_filter.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchRecipeFragment : Fragment(R.layout.fragment_search_recipe) {

    //todo:popup filter view
    //todo:toolbar in main
    //todo: add categories
    //todo: typeface
    //todo: filter

    private lateinit var binding: FragmentSearchRecipeBinding
    private val searchRecipeViewModel: SearchRecipeViewModel by viewModels()

    private lateinit var recipeAdapter : RecipesAdapter

    private var searchJob : Job? = null
    private var isRequestFocus = true

    private val args : SearchRecipeFragmentArgs by navArgs()

    private lateinit var bottomSheetDialog : BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchRecipeBinding.bind(view)

        binding.searchRecipeViewModel = searchRecipeViewModel
        binding.lifecycleOwner = this

        initUi()
    }

    private fun initUi () {

        //args from intent
        args.type?.let {
//            searchRecipeViewModel.addType(args.type!!)
            search()
            isRequestFocus = false
        }

        requestSearch()
        setUpSearchList()
        setUpTopFilterList()
        setUpBottomFilter()

        //search by edit
        binding.searchText.doAfterTextChanged {
            it?.let {searchText ->
                search(searchText.toString())
            }
        }

        binding.filter.setOnClickListener {
            showBottomFilter()
        }
    }

    private fun setUpBottomFilter() {

        bottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomFilterTheme
        )

        val view = LayoutInflater.from(requireContext())
            .inflate(
                R.layout.bottom_filter,
                bottom_filter_container
            )

        val addTypeListener = object: FilterAdapter.Listener {
            override fun interact(position: Int, item: TypeModel) {
                Timber.d("Add ${item.value}")
                searchRecipeViewModel.addType(item)
//                search()
            }
        }

        //type list (soup...)
        val filterAdapter = FilterAdapter(addTypeListener)
        filterAdapter.submitFiltersList(searchRecipeViewModel.getTypeList())
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

        with(view.type_recycler) {
            adapter = filterAdapter
            layoutManager = gridLayoutManager
        }

        bottomSheetDialog.setContentView(view)
    }

    private fun setUpSearchList() {

        val loadMoreListener : LoadMoreScrollListener

        val loadListener = object : LoadMoreScrollListener.LoadListener {
            override fun onLoad() {
                searchRecipeViewModel.searchRecipes(binding.searchText.text.toString())
            }
        }

        val recipeClickListener = object : RecipesAdapter.RecipeNavigator {
            override fun onRecipeClick(recipe: Recipe) {
                val action =
                    SearchRecipeFragmentDirections.actionSearchRecipeFragmentToRecipeInfoFragment(
                        recipe.id
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }

        val linearLayoutManager = LinearLayoutManager(context)
        loadMoreListener = LoadMoreScrollListener(linearLayoutManager, loadListener)

        recipeAdapter = RecipesAdapter(Constant.ORIENTATION_VERTICAL, recipeClickListener)

        binding.recipesRecycler.apply {
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

    private fun setUpTopFilterList() {

        val closeListener = object : FilterAdapter.Listener{
            override fun interact(position: Int, item: TypeModel) {
                searchRecipeViewModel.delType(item)
            }
        }

        val filterAdapter = FilterAdapter(closeListener)
        with (binding.filterRecycler) {
            adapter = filterAdapter
        }

        searchRecipeViewModel.topFilterList.observe(viewLifecycleOwner, Observer { types ->
            filterAdapter.submitFiltersList(types)
        })
    }

    private fun search(txt: String = "") {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(Constant.SEARCH_DELAY)
            if (txt.isNotEmpty()) {
                searchRecipeViewModel.cleanCurrentSearchList()
                searchRecipeViewModel.searchRecipes(txt)
            } else if (args.type != null) {
                searchRecipeViewModel.searchRecipes("")
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

    private fun stopLoading() {
        binding.loadingScreen.visibility = View.GONE
    }

    private fun startLoading() {
        binding.loadingScreen.visibility = View.VISIBLE
    }

    private fun showBottomFilter(){
        bottomSheetDialog.show()
    }

    override fun onPause() {
        super.onPause()
        isRequestFocus = false
    }
}
