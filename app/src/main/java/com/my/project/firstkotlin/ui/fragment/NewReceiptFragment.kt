package com.my.project.firstkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.my.project.firstkotlin.R
import com.my.project.firstkotlin.databinding.FragmentNewReceiptBinding
import com.my.project.firstkotlin.ui.base.BaseFragment
import com.my.project.firstkotlin.viewmodel.NewReceiptViewModel
import com.my.project.firstkotlin.viewmodel.ViewModelFactory

class NewReceiptFragment : BaseFragment() {

    private lateinit var binding: FragmentNewReceiptBinding
    private lateinit var newReceiptViewModel: NewReceiptViewModel

    override fun layoutId() = R.layout.fragment_new_receipt

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewReceiptBinding.bind(view)

        val factory = ViewModelFactory(activity!!.application)

        newReceiptViewModel = ViewModelProvider(this, factory).get(NewReceiptViewModel::class.java)
        binding.newReceiptViewModel = newReceiptViewModel
        binding.lifecycleOwner = this
    }
}