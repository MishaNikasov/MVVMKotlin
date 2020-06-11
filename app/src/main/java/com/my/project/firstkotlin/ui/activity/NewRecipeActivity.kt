package com.my.project.firstkotlin.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityNewRecipeBinding
import com.my.project.firstkotlin.ui.base.BaseActivity
import com.my.project.firstkotlin.ui.fragment.NewRecipeFragment

class NewRecipeActivity : BaseActivity() {

    lateinit var binding : ActivityNewRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_recipe)
        setFragment(R.id.container, NewRecipeFragment())
    }

}
