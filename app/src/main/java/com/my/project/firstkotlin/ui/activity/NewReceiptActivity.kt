package com.my.project.firstkotlin.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.ActivityMainBinding
import com.my.project.firstkotlin.ui.base.BaseActivity
import com.my.project.firstkotlin.ui.fragment.ReceiptListFragment

class NewReceiptActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_receipt)
        setFragment(R.id.container, ReceiptListFragment())
    }

}
