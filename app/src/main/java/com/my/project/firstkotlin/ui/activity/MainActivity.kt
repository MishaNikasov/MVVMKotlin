package com.my.project.firstkotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityMainBinding
import com.my.project.firstkotlin.ui.base.BaseActivity
import com.my.project.firstkotlin.ui.fragment.RecipeListFragment

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setFragment(R.id.container, RecipeListFragment())

        binding.fab.setOnClickListener {
            openNewRecipe()
        }
    }

    private fun openNewRecipe() {
        val intent = Intent(this, NewRecipeActivity::class.java)
        startActivity(intent)
    }

}
