package com.my.project.firstkotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityMainBinding
import com.my.project.firstkotlin.ui.fragment.ReceiptListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = ReceiptListFragment()
        fragmentTransaction.add(R.id.container, fragment)
        fragmentTransaction.commit()
    }

}
