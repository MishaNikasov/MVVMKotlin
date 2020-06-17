package com.my.project.firstkotlin.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityMainBinding
import com.my.project.firstkotlin.ui.base.BaseActivity
import com.my.project.firstkotlin.ui.fragment.NewRecipeFragment
import com.my.project.firstkotlin.ui.fragment.MainRecipeFragment
import com.my.project.firstkotlin.ui.fragment.SearchRecipeFragment

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    private val recipeListFragment = MainRecipeFragment()
    private val searchRecipeFragment = SearchRecipeFragment()
    private val newRecipeFragment = NewRecipeFragment()
    private var active : Fragment = recipeListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpBottomNavigation()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpBottomNavigation() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, newRecipeFragment, "New Recipe").hide(newRecipeFragment)
            add(R.id.container, searchRecipeFragment, "Search").hide(searchRecipeFragment)
            add(R.id.container, recipeListFragment, "List")
            commit()
        }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recipeListFragment -> {
                    supportFragmentManager.beginTransaction().hide(active).show(recipeListFragment).commit()
                    active = recipeListFragment
                    true
                }
                R.id.searchRecipeFragment -> {
                    supportFragmentManager.beginTransaction().hide(active).show(searchRecipeFragment).commit()
                    active = searchRecipeFragment
                    true
                }
                R.id.newRecipeFragment -> {
                    supportFragmentManager.beginTransaction().hide(active).show(newRecipeFragment).commit()
                    active = newRecipeFragment
                    true
                }
                else -> false
            }
        }
    }
}
