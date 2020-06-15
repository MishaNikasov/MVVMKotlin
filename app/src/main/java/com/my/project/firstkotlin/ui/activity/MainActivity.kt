package com.my.project.firstkotlin.ui.activity

import android.os.Bundle
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

        val naController = findNavController(R.id.fragment_container)

        binding.bottomNavigationView.setupWithNavController(naController)
    }

}
