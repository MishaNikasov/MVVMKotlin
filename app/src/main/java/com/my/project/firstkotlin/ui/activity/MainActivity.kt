package com.my.project.firstkotlin.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityMainBinding
import com.my.project.firstkotlin.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.recipeInfoFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

//    private fun setUpBottomNavigation() {
//
//        private val recipeListFragment = MainRecipeFragment()
//        private val searchRecipeFragment = SearchRecipeFragment()
//        private val newRecipeFragment = NewRecipeFragment()
//        private var active : Fragment = recipeListFragment
//
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.container, newRecipeFragment, "New Recipe").hide(newRecipeFragment)
//            add(R.id.container, searchRecipeFragment, "Search").hide(searchRecipeFragment)
//            add(R.id.container, recipeListFragment, "List")
//            commit()
//        }
//
//        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.recipeListFragment -> {
//                    supportFragmentManager.beginTransaction().hide(active).show(recipeListFragment).commit()
//                    active = recipeListFragment
//                    true
//                }
//                R.id.searchRecipeFragment -> {
//                    supportFragmentManager.beginTransaction().hide(active).show(searchRecipeFragment).commit()
//                    active = searchRecipeFragment
//                    true
//                }
//                R.id.newRecipeFragment -> {
//                    supportFragmentManager.beginTransaction().hide(active).show(newRecipeFragment).commit()
//                    active = newRecipeFragment
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}
